package com.example.teamtube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamtube.Constrant.Constrants
import com.example.teamtube.Retrofit.Model.Item
import com.example.teamtube.Retrofit.VideoNetworkClient.apiService
import com.example.teamtube.databinding.FragmentHomeBinding

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
            // spinner 선택 시 title 값 설정
            var selectedItem = items.filter { f -> f.snippet.title == text }
            // spinner 선택 시 channelId 값 설정
            var categoryId = items.filter { f -> f.id == text }
            binding.categoryVideos.text = selectedItem[0].snippet.title
            communicateVideo(categoryId[0].id)
        }
        return binding.root
    }

    // Category-Video Network 설정 -동
    private fun communicateCategoryVideo() {
        val response = apiService.getCategoryVideoInfo("snippet", "KR", Constrants.API_KEY).execute()


        // HomeData에 데이터 정보 추가
        val selectedList = items.map {
        }
    }

    // Video Network 설정 - 동기적으로
    private fun communicateVideo(id: String) {
        val response = apiService.getVideoInfo("snippet", "mostPopular", 10, id, Constrants.API_KEY)
    }
}
