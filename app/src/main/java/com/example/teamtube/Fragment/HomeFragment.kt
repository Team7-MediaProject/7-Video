package com.example.teamtube.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamtube.Constrant.Constrants
import com.example.teamtube.Adapter.HomeFragmentAdapter
import com.example.teamtube.ChannelData.ChannelApi
import com.example.teamtube.ChannelData.ChannelFragmentAdapter
import com.example.teamtube.ChannelData.ChannelModel
import com.example.teamtube.Retrofit.Model.Root
import com.example.teamtube.Retrofit.Model.VideoResponse
import com.example.teamtube.Retrofit.VideoNetworkClient.apiCategoryService
import com.example.teamtube.data.YouTubeApi
import com.example.teamtube.databinding.FragmentHomeBinding
import com.example.teamtube.model.HomeitemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    /*private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!*/

    private lateinit var adapterChannel: ChannelFragmentAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeFragmentAdapter

    private var category: List<String> = emptyList()
    private val resItems: MutableList<HomeitemModel> = mutableListOf()
    private val resItemsChannel: ArrayList<ChannelModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        //어댑터연결
        adapter = HomeFragmentAdapter(requireContext())
        val recyclerView: RecyclerView = binding.rvChannel
        adapterChannel = ChannelFragmentAdapter(requireContext())
        recyclerView.adapter = adapterChannel

        binding.rvChannel.adapter = adapterChannel
        binding.rvChannel.layoutManager = LinearLayoutManager(requireContext())

        binding.rvMostPopular.adapter = adapter
        binding.rvMostPopular.layoutManager = layoutManager

        fetchVideoResults()
        fetchChannelResult()

        val categorySpinner = binding.categoryVideos
        communicateCategoryVideo()
        Log.d("Calling", "communicateCategoryVideo")

        // 아이템 선택 event 설정
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 선택한 카테고리를 이용해 fetchVideo 해줄 것
                fetchCategoryVideoResults(position.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        return binding.root
    }

    private fun fetchVideoResults() {
        YouTubeApi.apiService.listVideos(
            part = "snippet,contentDetails",
            chart = "mostPopular",
            maxResults = 10,
            regionCode = "KR",
            apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ"
        ).enqueue(object : Callback<com.example.teamtube.data.Root> {
            override fun onResponse(
                call: Call<com.example.teamtube.data.Root>,
                response: Response<com.example.teamtube.data.Root>
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

            override fun onFailure(call: Call<com.example.teamtube.data.Root>, t: Throwable) {
                Log.e("YouTubeApi", "Error: ${t.message}")
            }
        })
    }

    private fun fetchChannelResult() {
        ChannelApi.apiService.listChannels(
            part = "snippet",
            maxResults = 10,
            apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ",
            id = "UC_x5XG1OV2P6uZZ5FSM9Ttw"
        ).enqueue(object : Callback<com.example.teamtube.data.Root> {
            override fun onResponse(
                call: Call<com.example.teamtube.data.Root>,
                response: Response<com.example.teamtube.data.Root>
            ) {
                if (response.isSuccessful) {
                    val channelData = response.body()
                    channelData?.items?.let { items ->
                        for (item in items) {
                            val thumbnails = item.snippet.thumbnails.high.url
                            val id = item.id
                            val title = item.snippet.title
                            resItemsChannel.add(ChannelModel(thumbnails, id, title))
                            Log.d("Channel", "Thunmbnails: $thumbnails, ID: $id")
                        }
                    }
                    adapterChannel.updateData(resItemsChannel)
                } else {
                    Log.e("API", "Error: ${response.code()}")
                }
                adapterChannel.itemsChannel = resItemsChannel
                adapterChannel.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<com.example.teamtube.data.Root>, t: Throwable) {
                Log.e("API", "onFailure: ${t.message}")
            }
        })
    }

    private fun communicateCategoryVideo() {
        apiCategoryService.getCategoryVideoInfo("snippet", "KR", Constrants.API_KEY)
            .enqueue(object : Callback<Root> {
                override fun onResponse(
                    call: Call<Root>,
                    response: Response<Root>
                ) {
                    if (response.isSuccessful) {
                        val categoryVideo = response.body()?.items ?: emptyList()
                        if (categoryVideo.isNotEmpty()) {
                            category = categoryVideo.map { it.snippet.title }
                            Log.d("CategoryVideo", "${category}")
                            // category Adapter 초기화 및 설정
                            val categoryAdapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                category
                            ).apply {
                                setDropDownViewResource(android.R.layout.simple_spinner_item)
                            }
                            binding.categoryVideos.adapter = categoryAdapter

                        }
                    } else {
                        Log.e("response Error", "Error : ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<Root>, t: Throwable) {
                    Log.e("CategoryVideo", "Error : ${t.message}")
                }

            })
    }


    private fun fetchCategoryVideoResults(id: String) {
        apiCategoryService.getVideoInfo(
            part = "snippet,contentDetails",
            chart = "mostPopular",
            maxResults = 10,
            videoCategoryId = id,
            regionCode = "KR",
            apiKey = Constrants.API_KEY
            //apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ"
        ).enqueue(object : Callback<VideoResponse> {
            override fun onResponse(
                call: Call<VideoResponse>,
                response: Response<VideoResponse>
            ) {
                if (response.isSuccessful) {
                    val videos = response.body()?.items ?: emptyList()
                    resItems.clear()
                    videos.forEach {
                        Log.d(
                            "YouTubeApi",
                            "Video ID: ${it.id}, Title: ${it.snippet.title}"
                        )
                        val id = it.snippet.categoryId
                        val title = it.snippet.title
                        val thumbnail = it.snippet.thumbnails.high.url
                        resItems.add(HomeitemModel(id, title, thumbnail))
                    }
                    adapter.updateData(resItems)
                    /*adapter3.setVideoItems(resItems)*/
                } else {
                    Log.e("YouTubeApi", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.e("YouTubeApi", "Error: ${t.message}")
            }
        })
    }
}
