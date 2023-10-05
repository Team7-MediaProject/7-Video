package com.example.teamtube.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.teamtube.Adapter.MyChannelFragmentAdapter
import com.example.teamtube.Adapter.MyVideoFragmentAdapter
import com.example.teamtube.MainActivity
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.Activity.VideoDetailActivity
import com.example.teamtube.databinding.FragmentMyVideoBinding

class MyVideoFragment : Fragment() {
    private var _binding: FragmentMyVideoBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private lateinit var detailActivity: VideoDetailActivity

    private lateinit var mContext: Context
    private var likedItems: List<ChannelModel> = listOf()
    private lateinit var channelAdapter: MyChannelFragmentAdapter
    private lateinit var videoAdapter: MyVideoFragmentAdapter

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
        channelAdapter = MyChannelFragmentAdapter(mContext)
        videoAdapter = MyVideoFragmentAdapter(mContext)

        _binding = FragmentMyVideoBinding.inflate(inflater, container, false).apply {
            likedChannelRecyclerView.adapter = channelAdapter
            likedChannelRecyclerView.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

            //val gridManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            //binding.likedVideoRecyclerView.layoutManager = gridManager
            likedVideoRecyclerView.apply {
                adapter = videoAdapter
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
            }
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        mainActivity = activity as MainActivity

        likedItems = mainActivity.likedItems
        channelAdapter.setChannelItems(likedItems)

        //adapter.setVideoItems(thumbnail = "", title = "")
        Log.d("like11", "like: $likedItems")
        Log.d("lifeCycle", "my_video_fragment onResume")
    }
}