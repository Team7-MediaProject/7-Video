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
import com.example.teamtube.Adapter.CategoryVideoAdapter
import com.example.teamtube.Constrant.Constrants
import com.example.teamtube.Adapter.HomeFragmentAdapter
import com.example.teamtube.ChannelData.ChannelFragmentAdapter
import com.example.teamtube.Model.CategoryDataList
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.Retrofit.ApiData.CategoryVideoData.Root
import com.example.teamtube.Retrofit.ApiData.CategoryVideoData.VideoResponse
import com.example.teamtube.Retrofit.retrofit.VideoNetworkClient.apiService
import com.example.teamtube.databinding.FragmentHomeBinding
import com.example.teamtube.Model.HomeitemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    /*private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!*/

    private lateinit var adapterMost: HomeFragmentAdapter
    private lateinit var adapterChannel: ChannelFragmentAdapter

    private lateinit var binding: FragmentHomeBinding
    private lateinit var videoAdapter: CategoryVideoAdapter

    private var category: List<String> = emptyList()
    private val resItems: MutableList<HomeitemModel> = mutableListOf()
    private val resItemsChannel: ArrayList<ChannelModel> = ArrayList()
    private val resItemsVideo: MutableList<HomeitemModel> = mutableListOf()
    private val categoryList = CategoryDataList().categoryList
    private lateinit var layoutManagerMost: LinearLayoutManager
    private lateinit var layoutManagerChannel: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //수평방향 스크롤
        layoutManagerMost = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        layoutManagerChannel = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        //어뎁터연결
        adapterMost = HomeFragmentAdapter(requireContext())
        adapterChannel = ChannelFragmentAdapter(requireContext())

        //채널
        binding.rvChannel.layoutManager = layoutManagerChannel
        binding.rvChannel.adapter = adapterChannel

        //인기동영상
        binding.rvMostPopular.layoutManager = layoutManagerMost
        binding.rvMostPopular.adapter = adapterMost

        videoAdapter = CategoryVideoAdapter(requireContext())
        binding.rvVideos.adapter = videoAdapter
        binding.rvVideos.layoutManager = LinearLayoutManager(requireContext())

        fetchVideoResults()
        fetchChannelResult()

        val categorySpinner = binding.categoryVideos
        communicateCategoryVideo()
        Log.d("CategoryVideo", "${resItemsChannel}")

        // 아이템 선택 event 설정
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categoryList[position].categoryId
                // 선택한 카테고리를 이용해 fetchVideo 해줄 것
                fetchCategoryVideoResults(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        return binding.root
    }

    private fun fetchVideoResults() {
        apiService.listVideos(
            part = "snippet,contentDetails",
            chart = "mostPopular",
            maxResults = 10,
            regionCode = "KR",
            apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ"
        ).enqueue(object : Callback<com.example.teamtube.Retrofit.ApiData.MostPopularData.Root> {
            override fun onResponse(
                call: Call<com.example.teamtube.Retrofit.ApiData.MostPopularData.Root>,
                response: Response<com.example.teamtube.Retrofit.ApiData.MostPopularData.Root>
            ) {
                if (response.isSuccessful) {
                    val videos = response.body()?.items ?: emptyList()
                    resItems.clear()
                    videos.forEach {
                        Log.d("YouTubeApi", "Video ID: ${it.id}, Title: ${it.snippet.title}")
                        val id = it.id
                        val title = it.snippet.title
                        val thumbnail = it.snippet.thumbnails.high.url
                        //val categoryTitle = it.snippet.categoryId
                        val description = it.snippet.description
                        resItems.add(HomeitemModel(id, title, thumbnail, description))
                    }
                    adapterMost.updateData(resItems)
                } else {
                    Log.e("YouTubeApi", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(
                call: Call<com.example.teamtube.Retrofit.ApiData.MostPopularData.Root>,
                t: Throwable
            ) {
                Log.e("YouTubeApi", "Error: ${t.message}")
            }
        })
    }

    private fun fetchChannelResult() {
        apiService.listChannels(
            part = "snippet",
            maxResults = 10,
            apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ",
            id = "UC_x5XG1OV2P6uZZ5FSM9Ttw, UCU_hKD03cUTCvnOJpEmKvCg, UCUj6rrhMTR9pipbAWBAMvUQ, UCcdlIcleb4oIK6of1ugSJ7w, UCyn-K7rZLXjGl7VXGweIlcA, UCtm_QoN2SIxwCE-59shX7Qg, UCPWFxcwPliEBMwJjmeFIDIg, UCiBr0bK06imaMbLc8sAEz0A, UC78PMQprrZTbU0IlMDsYZPw, UCL3gnarNIeI_M0cFxjNYdAA"
        ).enqueue(object : Callback<com.example.teamtube.Retrofit.ApiData.MostPopularData.Root> {
            override fun onResponse(call: Call<com.example.teamtube.Retrofit.ApiData.MostPopularData.Root>, response: Response<com.example.teamtube.Retrofit.ApiData.MostPopularData.Root>) {
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

            override fun onFailure(call: Call<com.example.teamtube.Retrofit.ApiData.MostPopularData.Root>, t: Throwable) {
                Log.e("API", "onFailure: ${t.message}")
            }
        })
    }

    private fun communicateCategoryVideo() {
        apiService.getCategoryVideoInfo("snippet", "KR", Constrants.API_KEY)
            .enqueue(object : Callback<Root> {
                override fun onResponse(
                    call: Call<Root>,
                    response: Response<Root>
                ) {
                    if (response.isSuccessful) {
                        val categoryVideo = response.body()?.items ?: emptyList()
                        if (categoryVideo.isNotEmpty()) {
                            category = categoryList.map { it.title }
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


    private fun fetchCategoryVideoResults(selectedId: String) {
        apiService.getVideoInfo(
            part = "snippet,contentDetails",
            chart = "mostPopular",
            maxResults = 10,
            videoCategoryId = selectedId,
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
                    resItemsVideo.clear()
                    videos.forEach {
                        Log.d("CategoryyyId", "$selectedId")
                        Log.d("id 값", "${it.snippet.title}")
                        val id = selectedId
                        val title = it.snippet.title
                        val thumbnail = it.snippet.thumbnails.high.url
                        //val categoryTitle = it.snippet.categoryId
                        val description = it.snippet.description
                        resItemsVideo.add(HomeitemModel(id, title, thumbnail, description))
                    }
                    videoAdapter.setVideoItems(resItemsVideo)
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
