package com.example.teamtube.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamtube.Adapter.MyVideoFragmentAdapter
import com.example.teamtube.MainActivity
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.databinding.FragmentMyVideoBinding

class MyVideoFragment : Fragment() {
    private var _binding: FragmentMyVideoBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private var likedItems: List<ChannelModel> = listOf()
    private lateinit var adapter: MyVideoFragmentAdapter

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
        Log.d("lifeCycle", "my_video_fragment onCreateView")



        adapter = MyVideoFragmentAdapter(mContext)

        _binding = FragmentMyVideoBinding.inflate(inflater, container, false).apply {
            likedChannelRecyclerView.adapter = adapter
            likedChannelRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifeCycle", "my_video_fragment onStart")
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        likedItems = mainActivity.likedItems

        adapter.setItems(likedItems)
        Log.d("like11","like: $likedItems")
        Log.d("lifeCycle", "my_video_fragment onResume")
    }
}
