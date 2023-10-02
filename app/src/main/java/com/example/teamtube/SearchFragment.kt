package com.example.teamtube

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.teamtube.SearchFragmentData.RetrofitClient.apiService
import com.example.teamtube.SearchFragmentData.SearchData
import com.example.teamtube.SearchFragmentData.SearchItem
import com.example.teamtube.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!
    private val KEY = "AIzaSyB2BlIqR-ySDZYz2NBP7uysaZY1rA7OuhM"
    private lateinit var adapter: SearchFragmentAdapter
    private var resItems: ArrayList<SearchData> = ArrayList()
    private lateinit var mContext: Context
    fun togglebtn(view: View) {
        listOf(
            binding.sfBtnAsmr,
            binding.sfBtnGame,
            binding.sfBtnHealing,
            binding.sfBtnMovie
        ).forEach {
            if (it != view) {
                it.isSelected = false
            }
        }
        view.isSelected = !view.isSelected
        if (view.isSelected) {
            val query = binding.sfEtSearch.text.toString()
            if (query.isNotEmpty()) {
                adapter.clearItem()
                searchVideo(query, (view as Button).text.toString())

            } else {
                view.isSelected = false
                Toast.makeText(mContext, "검색어 입력해야지", Toast.LENGTH_SHORT).show()
            }
        } else {
            val query = binding.sfEtSearch.text.toString()
            adapter.clearItem()
            searchVideo(query, "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        val recyclerView: RecyclerView = binding.sfRv
        adapter = SearchFragmentAdapter(mContext)
        recyclerView.adapter = adapter

        val btnHealing = binding.sfBtnHealing
        val btnAsmr = binding.sfBtnAsmr
        val btnGame = binding.sfBtnGame
        val btnMovie = binding.sfBtnMovie

        listOf(btnAsmr, btnGame, btnHealing, btnMovie).forEach {
            it.setOnClickListener {
                togglebtn(it)
            }
        }

        binding.sfBtnSearch.setOnClickListener {
            val query = binding.sfEtSearch.text.toString()
            if (query.isNotEmpty()) {
                adapter.clearItem()
                searchVideo(query, "")
            } else {
                Toast.makeText(mContext, "검색어 입력해야지", Toast.LENGTH_SHORT).show()
            }

            hideKeyboard()

        }
        setupView()
        return binding.root

    }


    private fun setupView() {
        val gridManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.sfRv.layoutManager = gridManager

        adapter = SearchFragmentAdapter(mContext)
        binding.sfRv.adapter = adapter
        binding.sfRv.itemAnimator = null

    }

    private fun searchVideo(query: String, category: String) {
        val query = "$category + $query"
        apiService.videoSearch(
            apiKey = KEY,
            part = "snippet",
            maxResults = 30,
            q = query,
            type = "video",
            regionCode = "KR"
        )
            ?.enqueue(object : Callback<SearchItem?> {
                override fun onResponse(call: Call<SearchItem?>, response: Response<SearchItem?>) {
                    if (response.isSuccessful) {
                        val searchData = response.body()
                        searchData?.items?.let { items ->
                            for (item in items) {
                                val thumbnails = item.snippet.thumbnails.high.url
                                val title = item.snippet.title
                                val id = item.id.videoId
                                resItems.add(SearchData(title, thumbnails, id))


                                Log.d("videoSearch", "thumbnails:$thumbnails, title:$title, id:$id")
                            }
                        }
                    } else {
                        Log.e("VideoSearch", "fail: ${response.code()}")
                    }

                    adapter.items = resItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<SearchItem?>, t: Throwable) {

                }

            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.sfEtSearch.windowToken, 0)
    }

}