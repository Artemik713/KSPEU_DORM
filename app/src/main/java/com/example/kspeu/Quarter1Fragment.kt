package com.example.kspeu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kspeu.databinding.FragmentQuarter1Binding
import com.example.kspeu.Achievement

class Quarter1Fragment : Fragment() {
    private var _binding: FragmentQuarter1Binding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AchievementAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuarter1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = AchievementAdapter()
        binding.quarterRecyclerView.adapter = adapter
        binding.quarterRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadData() {
        // Здесь мы будем загружать данные для первого квартала
        val achievements = listOf(
            Achievement("2024-01-15", "Участие в олимпиаде", "10", 1),
            Achievement("2024-02-20", "Победа в конкурсе", "20", 1),
            Achievement("2024-03-10", "Публикация статьи", "15", 1)
        )
        adapter.submitList(achievements)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}