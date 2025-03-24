package com.example.kspeu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.kspeu.databinding.StudentsPointsViewBinding

class StudentsPointsViewFragment : Fragment() {

    private var _binding: StudentsPointsViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var achievementRepository: AchievementRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StudentsPointsViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        achievementRepository = AchievementRepository()

        val items = listOf("2025-2024", "2023-2024", "2022-2023", "2021-2022")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.dropdownTextView.setAdapter(adapter)

        val listPopupWindow = ListPopupWindow(requireContext())
        listPopupWindow.setAdapter(adapter)
        listPopupWindow.anchorView = binding.dropdownTextView
        listPopupWindow.setBackgroundDrawable(resources.getDrawable(R.color.white, null))
        listPopupWindow.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = adapter.getItem(position)
            binding.dropdownTextView.setText(selectedItem, false)
            listPopupWindow.dismiss()
        }
        binding.dropdownTextView.setOnClickListener {
            listPopupWindow.show()
        }

        binding.quarter1Button.setOnClickListener {
            findNavController().navigate(R.id.action_studentsPointsViewFragment_to_quarter1Fragment)
        }
        binding.addAchievementButton.setOnClickListener {
            findNavController().navigate(R.id.addAchievementFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}