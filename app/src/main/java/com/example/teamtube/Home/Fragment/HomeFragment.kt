package com.example.teamtube.Home.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamtube.CategoryList
import com.example.teamtube.Constrant.Constrants
import com.example.teamtube.Home.Adapter.HomeFragmentAdapter
import com.example.teamtube.Retrofit.Model.CategoryVideo
import com.example.teamtube.Retrofit.Model.Item
import com.example.teamtube.Retrofit.Model.Video
import com.example.teamtube.Retrofit.VideoNetworkClient.apiCategoryService
import com.example.teamtube.databinding.FragmentHomeBinding
import com.example.teamtube.model.HomeitemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    /*private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!*/
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeFragmentAdapter

    private var category: List<CategoryList> = emptyList()
    private val resItems: MutableList<HomeitemModel> = mutableListOf()
    private var item = mutableListOf<Item>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = HomeFragmentAdapter(requireContext())
        binding.rvMostPopular.adapter = adapter
        binding.rvMostPopular.layoutManager = LinearLayoutManager(requireContext())

        /*        adapter3 = CategoryVideoAdapter(requireContext())
                binding.recyclerView3.adapter = adapter3
                binding.recyclerView3.layoutManager = LinearLayoutManager(requireContext())*/

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

                //fetchVideoResults(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        return binding.root
    }

    private fun communicateCategoryVideo() {
        apiCategoryService.getCategoryVideoInfo("snippet", "KR", Constrants.API_KEY)
            .enqueue(object : Callback<CategoryVideo> {
                override fun onResponse(
                    call: Call<CategoryVideo>,
                    response: Response<CategoryVideo>
                ) {
                    val categoryVideo = response.body()?.root?.items ?: emptyList()
                    if (categoryVideo.isNotEmpty()) {
                        category = item.map {
                            CategoryList(it.id, it.snippet.title)
                        }

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
                }

                override fun onFailure(call: Call<CategoryVideo>, t: Throwable) {
                    Log.e("CategoryVideo", "Error : ${t.message}")
                }
            })
        /*val respnose = apiCategoryService.getCategoryVideoInfo("snippet", "KR", Constrants.API_KEY)
        item = respnose.root.items

        category = item.map {
            CategoryList(it.id, it.snippet.title)
        }

        handler.post {

        }*/

    }

    private fun fetchVideoResults(id: String) {
        apiCategoryService.getVideoInfo(
            part = "snippet,contentDetails",
            chart = "mostPopular",
            maxResults = 10,
            videoCategoryId = id,
            regionCode = "KR",
            apiKey = Constrants.API_KEY
            //apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ"
        ).enqueue(object : Callback<Video> {
            override fun onResponse(
                call: Call<Video>,
                response: Response<Video>
            ) {
                if (response.isSuccessful) {
                    val videos = response.body()?.response?.items ?: emptyList()
                    resItems.clear()
                    videos.forEach {
                        Log.d("YouTubeApi", "Video ID: ${it.id}, Title: ${it.snippet.title}")
                        val id = it.snippet.categoryId
                        val title = it.snippet.title
                        val thumbnail = it.snippet.thumbnails.high.url
                        resItems.add(HomeitemModel(id, title, thumbnail))
                    }
                    /*if (response.isSuccessful) {
                        val videos = response.body()?.items ?: emptyList()
                        resItems.clear()
                        videos.forEach {
                            Log.d("YouTubeApi", "Video ID: ${it.id}, Title: ${it.snippet.title}")
                            val id = it.id
                            val title = it.snippet.title
                            val thumbnail = it.snippet.thumbnails.high.url
                            resItems.add(HomeitemModel(id, title, thumbnail))
                        }*/
                    adapter.updateData(resItems)
                    /*adapter3.setVideoItems(resItems)*/
                } else {
                    Log.e("YouTubeApi", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Video>, t: Throwable) {
                Log.e("YouTubeApi", "Error: ${t.message}")
            }
        })
    }
}

