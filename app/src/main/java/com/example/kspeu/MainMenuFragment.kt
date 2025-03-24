package com.example.kspeu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kspeu.databinding.FragmentStudentMainMenuBinding

class MainMenuFragment : Fragment() {

    private var _binding: FragmentStudentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработчик нажатия на кнопку "Баллы"
        binding.pointsButton.setOnClickListener {
            // Переход на StudentsPointsViewFragment
            findNavController().navigate(R.id.action_mainMenuFragment_to_studentsPointsViewFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}