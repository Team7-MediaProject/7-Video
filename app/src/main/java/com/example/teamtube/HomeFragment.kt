package com.example.teamtube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamtube.ChannelData.ChannelApi
import com.example.teamtube.data.Root
import com.example.teamtube.data.YouTubeApi.apiService
import com.example.teamtube.ChannelData.ChannelApi.apiService
import com.example.teamtube.ChannelData.ChannelFragmentAdapter
import com.example.teamtube.ChannelData.ChannelModel
import com.example.teamtube.data.YouTubeApi
import com.example.teamtube.databinding.FragmentHomeBinding
import com.example.teamtube.model.HomeitemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HomeFragmentAdapter
    private lateinit var adapterChannel: ChannelFragmentAdapter

    private val resItems: MutableList<HomeitemModel> = mutableListOf()
    private val resItemsChannel: ArrayList<ChannelModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        //수평방향 스크롤
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        //어뎁터연결
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

        return view
    }

    private fun fetchVideoResults() {
        YouTubeApi.apiService.listVideos(
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

    private fun fetchChannelResult() {
        ChannelApi.apiService.listChannels(
            part = "snippet",
            maxResults = 10,
            apikey = "AIzaSyBDAlTp9FuXH4pV_cJqcrJkbL2PFA4_-qQ",
            id = "UC_x5XG1OV2P6uZZ5FSM9Ttw"
        ).enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {
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

            override fun onFailure(call: Call<Root>, t: Throwable) {
                Log.e("API", "onFailure: ${t.message}")
            }
        })
    }
}
