package com.example.teamtube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.teamtube.Constrant.Constrants
import com.example.teamtube.Retrofit.Model.Item
import com.example.teamtube.Retrofit.Model.Snippets
import com.example.teamtube.Retrofit.Model.Video
import com.example.teamtube.Retrofit.Model.VideoItem
import com.example.teamtube.Retrofit.VideoNetworkClient.apiService
import com.example.teamtube.databinding.FragmentHomeBinding
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var adapter: HomeFragmentAdapter
    var items = listOf<Item>()

    /*private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        /*_binding = FragmentHomeBinding.inflate(inflater, container, false)*/
        binding.recyclerView3.layoutManager = LinearLayoutManager(context)
        adapter = HomeFragmentAdapter(requireContext())
        binding.recyclerView3.adapter = adapter

        binding.categoryVideos.setOnSpinnerItemSelectedListener<IconSpinnerItem> { _, _, _, item ->
            // spinner 선택 시 category 값 설정
            val selectedCategory = item.text.toString()
            val selectedItem = items.find { it.snippet.title == selectedCategory }
            // 선택한 카테고리 id 가져와서 동영상 목록 설정
            selectedItem?.id?.let { categoryId ->
                communicateVideo(categoryId)
            }
        }
        // 카테고리 API 설정
        communicateCategoryVideo()

        return binding.root
    }

    private fun communicateCategoryVideo() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    apiService.getCategoryVideoInfo("snippet", "KR", Constrants.API_KEY).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    // 카테고리를 powerSpinner에 설정
                    responseBody?.items?.let { categoryList ->
                        // spinner로 어댑터 설정
                        val adapter = IconSpinnerAdapter(binding.categoryVideos)
                        val categoryItem = categoryList.map {
                            IconSpinnerItem(it.snippet.title)
                        }
                        adapter.setItems(categoryItem)
                        Log.d("categoryItem", "$categoryItem")
                        binding.categoryVideos.selectItemByIndex(0)
                        items = categoryList
                        Log.d("item", "$items")
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "error", e)
            }
        }
    }

    private fun communicateVideo(id: String) {
        try {
            val response =
                apiService.getVideoInfo("snippet", "mostPopular", 10, id, Constrants.API_KEY)
                    .execute()
            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.item
                val videoItems = responseBody?.response?.items ?: emptyList()

                adapter.setVideoItems(videoItems)
                adapter.notifyDataSetChanged()

            }
        } catch (e: Exception) {

        }
    }
}
