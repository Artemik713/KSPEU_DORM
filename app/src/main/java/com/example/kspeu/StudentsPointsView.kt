package com.example.kspeu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import com.example.kspeu.databinding.StudentsPointsViewBinding

class StudentsPointsViewFragment : Fragment() {

    private var _binding: StudentsPointsViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StudentsPointsViewBinding.inflate(inflater, container, false)
        val view = binding.root

        val items = listOf("2024-2025","2023-2024", "2022-2023", "2021-2022")
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
        binding.addAchievementButton.setOnClickListener {
            // Здесь будет код, который выполняется при нажатии на кнопку
            // Например, переход на другой фрагмент или открытие диалогового окна
            // Пока просто выведем сообщение в лог
            println("Кнопка 'Добавить достижения' нажата!")
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}