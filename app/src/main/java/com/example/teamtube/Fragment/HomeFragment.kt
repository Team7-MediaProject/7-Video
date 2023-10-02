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
import com.example.teamtube.CategoryVideoData.CategoryList
import com.example.teamtube.ChannelData.ChannelApi
import com.example.teamtube.ChannelData.ChannelFragmentAdapter
import com.example.teamtube.ChannelData.ChannelModel
import com.example.teamtube.MostPopularData.YouTubeApi
import com.example.teamtube.CategoryVideoData.Model.Root
import com.example.teamtube.CategoryVideoData.Model.VideoResponse
import com.example.teamtube.CategoryVideoData.VideoNetworkClient.apiCategoryService
import com.example.teamtube.databinding.FragmentHomeBinding
import com.example.teamtube.model.HomeitemModel
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
    private val resItemsVideo: MutableList<CategoryList> = mutableListOf()

    private lateinit var layoutManagerMost: LinearLayoutManager
    private lateinit var layoutManagerChannel: LinearLayoutManager

    val categoryList: List<com.example.teamtube.CategoryVideoData.Model.CategoryList> = listOf(
        com.example.teamtube.CategoryVideoData.Model.CategoryList("1", "Film & Animation"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("2", "Autos & Vehicles"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("10", "Music"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("15", "Pets & Animals"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("17", "Sports"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("18", "Short Movies"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("19", "Travel & Events"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("20", "Gaming"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("21", "Videoblogging"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("22", "People & Blogs"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("23", "Comedy"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("24", "Entertainment"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("25", "News & Politics"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("26", "Howto & Style"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("27", "Education"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("28", "Science & Technology"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("30", "Movies"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("31", "Anime/Animation"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("32", "Action/Adventure"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("33", "Classics"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("34", "Comedy"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("35", "Documentary"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("36", "Drama"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("37", "Family"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("38", "Foreign"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("39", "Horror"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("40", "Sci-Fi/Fantasy"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("41", "Thriller"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("42", "Shorts"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("43", "Shows"),
        com.example.teamtube.CategoryVideoData.Model.CategoryList("44", "Trailers"),
    )
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
        YouTubeApi.apiService.listVideos(
            part = "snippet,contentDetails",
            chart = "mostPopular",
            maxResults = 10,
            regionCode = "KR",
            apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ"
        ).enqueue(object : Callback<com.example.teamtube.MostPopularData.Root> {
            override fun onResponse(
                call: Call<com.example.teamtube.MostPopularData.Root>,
                response: Response<com.example.teamtube.MostPopularData.Root>
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
                    adapterMost.updateData(resItems)
                } else {
                    Log.e("YouTubeApi", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(
                call: Call<com.example.teamtube.MostPopularData.Root>,
                t: Throwable
            ) {
                Log.e("YouTubeApi", "Error: ${t.message}")
            }
        })
    }

    private fun fetchChannelResult() {
        ChannelApi.apiService.listChannels(
            part = "snippet",
            maxResults = 10,
            apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ",
            id = "UC_x5XG1OV2P6uZZ5FSM9Ttw, UCU_hKD03cUTCvnOJpEmKvCg, UCUj6rrhMTR9pipbAWBAMvUQ, UCcdlIcleb4oIK6of1ugSJ7w, UCyn-K7rZLXjGl7VXGweIlcA, UCtm_QoN2SIxwCE-59shX7Qg, UCPWFxcwPliEBMwJjmeFIDIg, UCiBr0bK06imaMbLc8sAEz0A, UC78PMQprrZTbU0IlMDsYZPw, UCL3gnarNIeI_M0cFxjNYdAA"
        ).enqueue(object : Callback<com.example.teamtube.MostPopularData.Root> {
            override fun onResponse(call: Call<com.example.teamtube.MostPopularData.Root>, response: Response<com.example.teamtube.MostPopularData.Root>) {
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

            override fun onFailure(call: Call<com.example.teamtube.MostPopularData.Root>, t: Throwable) {
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


    private fun fetchCategoryVideoResults(selectedId: String) {
        apiCategoryService.getVideoInfo(
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
                        Log.d("id 값", "$it.")
                        val id = selectedId
                        val title = it.snippet.title
                        val thumbnail = it.snippet.thumbnails.high.url
                        resItemsVideo.add(CategoryList(id, title, thumbnail))
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
