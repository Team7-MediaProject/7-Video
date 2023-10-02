package com.example.teamtube.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamtube.databinding.FragmentMyVideoBinding

class MyVideoFragment : Fragment() {
    private var _binding: FragmentMyVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMyVideoBinding.inflate(inflater, container, false)
        return binding.root
    }
}