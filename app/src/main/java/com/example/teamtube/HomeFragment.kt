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
import com.example.teamtube.MostPopularData.Root
import com.example.teamtube.ChannelData.ChannelFragmentAdapter
import com.example.teamtube.ChannelData.ChannelModel
import com.example.teamtube.MostPopularData.YouTubeApi
import com.example.teamtube.databinding.FragmentHomeBinding
import com.example.teamtube.model.HomeitemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterMost: HomeFragmentAdapter
    private lateinit var adapterChannel: ChannelFragmentAdapter

    private val resItems: MutableList<HomeitemModel> = mutableListOf()
    private val resItemsChannel: ArrayList<ChannelModel> = ArrayList()

    private lateinit var layoutManagerMost: LinearLayoutManager
    private lateinit var layoutManagerChannel: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

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
                    adapterMost.updateData(resItems)
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
            id = "UC_x5XG1OV2P6uZZ5FSM9Ttw, UCU_hKD03cUTCvnOJpEmKvCg, UCUj6rrhMTR9pipbAWBAMvUQ, UCcdlIcleb4oIK6of1ugSJ7w, UCyn-K7rZLXjGl7VXGweIlcA, UCtm_QoN2SIxwCE-59shX7Qg, UCPWFxcwPliEBMwJjmeFIDIg, UCiBr0bK06imaMbLc8sAEz0A, UC78PMQprrZTbU0IlMDsYZPw, UCL3gnarNIeI_M0cFxjNYdAA"
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
