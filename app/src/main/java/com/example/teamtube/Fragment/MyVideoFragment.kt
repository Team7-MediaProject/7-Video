package com.example.teamtube.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.teamtube.Adapter.MyVideoAdapter
import com.example.teamtube.Adapter.MyVideoFragmentAdapter
import com.example.teamtube.MainActivity
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.Model.HomeitemModel
import com.example.teamtube.Retrofit.ApiData.SearchData.Thumbnails
import com.example.teamtube.VideoDetailActivity
import com.example.teamtube.databinding.FragmentMyVideoBinding

class MyVideoFragment : Fragment() {
    private var _binding: FragmentMyVideoBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private var likedItems: List<ChannelModel> = listOf()
    private var likedVideo: List<HomeitemModel> = listOf()
    private lateinit var adapter: MyVideoFragmentAdapter
    private lateinit var _adapter: MyVideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        adapter = MyVideoFragmentAdapter(mContext)
        _adapter = MyVideoAdapter(mContext)

        _binding = FragmentMyVideoBinding.inflate(inflater, container, false).apply {
            likedChannelRecyclerView.adapter = adapter
            likedChannelRecyclerView.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

            likedVideoRecyclerView.adapter = _adapter
            likedVideoRecyclerView.layoutManager =
                GridLayoutManager(mContext,2,LinearLayoutManager.VERTICAL,false)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        likedItems = mainActivity.likedItems
        adapter.setChannelItems(likedItems)
        Log.d("like11", "like: $likedItems")
        Log.d("lifeCycle", "my_video_fragment onResume")

//        val videoDetailActivity = activity as VideoDetailActivity
//        likedVideo = videoDetailActivity.likedItems
//        _adapter.setVideoItems(likedVideo)
//        Log.d("like22", "like: $likedVideo")
    }

}