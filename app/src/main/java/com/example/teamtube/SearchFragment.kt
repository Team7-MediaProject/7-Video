package com.example.teamtube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamtube.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val btnHealing = binding.sfBtnHealing
        val btnAsmr = binding.sfBtnAsmr
        val btnGame = binding.sfBtnGame
        val btnMovie = binding.sfBtnMovie

        var isHealingSelected = false
        var isAsmrSelected = false
        var isGameSelected = false
        var isMovieSelected = false

        btnHealing.setOnClickListener {
            isHealingSelected = !isHealingSelected
            btnHealing.isSelected = isHealingSelected
            isAsmrSelected = false
            btnAsmr.isSelected = false
            isGameSelected = false
            btnGame.isSelected = false
            isMovieSelected = false
            btnMovie.isSelected = false
        }

        btnAsmr.setOnClickListener {
            isAsmrSelected = !isAsmrSelected
            btnAsmr.isSelected = isAsmrSelected
            isHealingSelected = false
            btnHealing.isSelected = false
            isGameSelected = false
            btnGame.isSelected = false
            isMovieSelected = false
            btnMovie.isSelected = false
        }

        btnGame.setOnClickListener {
            isGameSelected =!isGameSelected
            btnGame.isSelected = isGameSelected
            isHealingSelected = false
            btnHealing.isSelected = false
            isAsmrSelected = false
            btnAsmr.isSelected = false
            isMovieSelected = false
            btnMovie.isSelected = false
        }

        btnMovie.setOnClickListener {
            isMovieSelected = !isMovieSelected
            btnMovie.isSelected = isMovieSelected
            isHealingSelected = false
            btnHealing.isSelected = false
            isAsmrSelected = false
            btnAsmr.isSelected = false
            isGameSelected = false
            btnGame.isSelected = false
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}