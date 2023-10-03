package com.example.teamtube.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val mainActivity = activity as MainActivity
        likedItems = mainActivity.likedItems

        adapter = MyVideoFragmentAdapter(mContext).apply {
            itemsChannel = likedItems.toMutableList()
        }

        _binding = FragmentMyVideoBinding.inflate(inflater, container, false).apply {
            likedChannelRecyclerView.adapter = adapter
        }
        return binding.root
    }
}
