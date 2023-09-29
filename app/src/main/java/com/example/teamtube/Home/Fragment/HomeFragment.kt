package com.example.teamtube.Home.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamtube.Constrant.Constrants
import com.example.teamtube.Home.Adapter.CategoryVideoAdapter
import com.example.teamtube.Home.Adapter.HomeFragmentAdapter
import com.example.teamtube.Retrofit.Model.CategoryVideo
import com.example.teamtube.Retrofit.Model.Item
import com.example.teamtube.Retrofit.Model.VideoItem
import com.example.teamtube.Retrofit.VideoNetworkClient.apiCategoryService
import com.example.teamtube.data.Root
import com.example.teamtube.data.YouTubeApi.apiService
import com.example.teamtube.databinding.FragmentHomeBinding
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.example.teamtube.model.HomeitemModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeFragmentAdapter
    private lateinit var adapter3 : CategoryVideoAdapter

    var videoCategoryItems = mutableListOf<Item>()
    private val resItems: MutableList<HomeitemModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = HomeFragmentAdapter(requireContext())
        binding.rvMostPopular.adapter = adapter
        binding.rvMostPopular.layoutManager = LinearLayoutManager(requireContext())

        adapter3 = CategoryVideoAdapter(requireContext())
        binding.recyclerView3.adapter = adapter
        binding.recyclerView3.layoutManager = LinearLayoutManager(requireContext())

        communicateCategoryVideo()
        fetchVideoResults()

        return binding.root
    }

    private fun communicateCategoryVideo() {
        apiCategoryService.getCategoryVideoInfo("snippet", "KR", Constrants.API_KEY)
            .enqueue(object : Callback<CategoryVideo> {
                override fun onResponse(
                    call: Call<CategoryVideo>,
                    response: Response<CategoryVideo>
                ) {
                    if(response.isSuccessful) {
                        val categoryVideo = response.body()?.root?.items ?: emptyList()
                        if(categoryVideo.isNotEmpty()) {
                            val categoryId = categoryVideo[0].snippet.title
                        }
                    }
                }

                override fun onFailure(call: Call<CategoryVideo>, t: Throwable) {
                    Log.e("CategoryVideo", "Error : ${t.message}")
                }

            })
    }

    private fun fetchVideoResults() {
        apiService.listVideos(
            part = "snippet,contentDetails",
            chart = "mostPopular",
            maxResults = 10,
            regionCode = "KR",
            apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ"
        ).enqueue(object : Callback<Root> {
            override fun onResponse(
                call: Call<Root>,
                response: Response<Root>
            ) {
                if (response.isSuccessful) {
                    val videos = response.body()?.items ?: emptyList()
                    resItems.clear()
                    videos.forEach {
                        Log.d("YouTubeApi", "Video ID: ${it.id}, Title: ${it.snippet.title}")
                        val id = it.id
                        val title = it.snippet.title
                        val thumbnail = it.snippet.thumbnails.high.url
                        resItems.add(HomeitemModel(id, title, thumbnail))
                    }
                    adapter.updateData(resItems)
                } else {
                    Log.e("YouTubeApi", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Root>, t: Throwable) {
                Log.e("YouTubeApi", "Error: ${t.message}")
            }
        })
    }

}

