package com.example.teamtube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.teamtube.Retrofit.Model.Item
import com.example.teamtube.Retrofit.VideoNetworkClient
import com.example.teamtube.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
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
        binding.categoryVideos.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            var selectedItem = items.filter { f -> f.snippet.title == text }
            var categoryId = items.filter { f -> f.snippet.channelId == text}
            binding.categoryVideos.text = selectedItem[0].snippet.title
        }
        return binding.root
    }

    private fun communicateCategoryVideo(param: HashMap<String, String>) = lifecycleScope.launch {
        val response = VideoNetworkClient.categoryVideoService.getCategoryVideoInfo(param)
        items = response.items

        // HomeData에 데이터 정보 추가
        val selectedList = items.map {
        }
    }

    private fun communicateVideo(param: HashMap<String, String>) = lifecycleScope.launch {
        val response = VideoNetworkClient.VideoService.getVideoInfo(param)
        items = response.
    }
}
