package com.example.teamtube

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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

        var isHealingSelected = false
        var isAsmrSelected = false
        var isGameSelected = false
        var isMovieSelected = false

        btnHealing.setOnClickListener {
            isHealingSelected = !isHealingSelected
            btnHealing.isSelected = isHealingSelected
            isAsmrSelected = false
            btnAsmr.isSelected = false
            isGameSelected = false
            btnGame.isSelected = false
            isMovieSelected = false
            btnMovie.isSelected = false
        }

        btnAsmr.setOnClickListener {
            isAsmrSelected = !isAsmrSelected
            btnAsmr.isSelected = isAsmrSelected
            isHealingSelected = false
            btnHealing.isSelected = false
            isGameSelected = false
            btnGame.isSelected = false
            isMovieSelected = false
            btnMovie.isSelected = false
        }

        btnGame.setOnClickListener {
            isGameSelected =!isGameSelected
            btnGame.isSelected = isGameSelected
            isHealingSelected = false
            btnHealing.isSelected = false
            isAsmrSelected = false
            btnAsmr.isSelected = false
            isMovieSelected = false
            btnMovie.isSelected = false
        }

        btnMovie.setOnClickListener {
            isMovieSelected = !isMovieSelected
            btnMovie.isSelected = isMovieSelected
            isHealingSelected = false
            btnHealing.isSelected = false
            isAsmrSelected = false
            btnAsmr.isSelected = false
            isGameSelected = false
            btnGame.isSelected = false
        }

        binding.sfBtnSearch.setOnClickListener {
            val query = binding.sfEtSearch.text.toString()
            if (query.isNotEmpty()) {
               adapter.clearItem()
                searchVideo(query)
            } else {
                Toast.makeText(mContext,"검색어 입력해야지", Toast.LENGTH_SHORT).show()
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

    private fun searchVideo(query: String){
        apiService.videoSearch(apiKey = KEY , part = "snippet",maxResults = 30, q = query, type = "video",regionCode = "KR")
            ?.enqueue(object : Callback<SearchItem?> {
                override fun onResponse(call: Call<SearchItem?>, response: Response<SearchItem?>) {
                    if (response.isSuccessful) {
                        val searchData = response.body()
                        searchData?.items?.let { items ->
                            for (item in items) {
                                val thumbnails = item.snippet.thumbnails.high.url
                                val title = item.snippet.title
                                val id = item.id.videoId
                                resItems.add(SearchData(title,thumbnails, id))


                                Log.d("videoSearch","thumbnails:$thumbnails, title:$title, id:$id")
                            }
                        }
                    } else {
                        Log.e("VideoSearch","fail: ${response.code()}")
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
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.sfEtSearch.windowToken, 0)
    }


    //test

}