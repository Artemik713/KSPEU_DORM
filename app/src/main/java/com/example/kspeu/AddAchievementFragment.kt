package com.example.kspeu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.kspeu.databinding.FragmentAddAchievementBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.util.Log



class AddAchievementFragment : Fragment() {

    private var _binding: FragmentAddAchievementBinding? = null
    private val binding get() = _binding!!
    private lateinit var documentPickerLauncher: ActivityResultLauncher<Intent>
    private var selectedDocumentUri: Uri? = null
    private lateinit var selectedDocumentNameTextView: TextView
    private var quarter: Int = 0
    private lateinit var achievementRepository: AchievementRepository

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*" // Allow all file types
        }
        documentPickerLauncher.launch(intent)
    }

    private fun getQuarterByDate(dateString: String): Int {
        //dateString format "dd.MM.yyyy"
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = dateFormat.parse(dateString) ?: return 0

        val calendar = Calendar.getInstance()
        calendar.time = date

        val month = calendar.get(Calendar.MONTH) + 1 // Month is 0-indexed

        return when (month) {
            in 9..11 -> 1 // September to November
            in 12..2 -> 2 // December to February
            in 3..5 -> 3 // March to May
            in 6..8 -> 4 // June to August
            else -> 0 // Invalid month
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAchievementBinding.inflate(inflater, container, false)
        selectedDocumentNameTextView = binding.selectedDocumentNameTextView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        achievementRepository = AchievementRepository()

        documentPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                selectedDocumentUri = result.data?.data
                val fileName = getFileName(selectedDocumentUri)
                selectedDocumentNameTextView.text = fileName
            }
        }

        binding.uploadDocumentButton.setOnClickListener {
            openDocumentPicker()
        }
        binding.saveAchievementButton.setOnClickListener {
            val dateString = binding.achievementDateEditText.text.toString()
            val description = binding.achievementDescriptionEditText.text.toString()
            val points = binding.achievementPointsEditText.text.toString()
            quarter = getQuarterByDate(dateString)
            Log.d("AddAchievementFragment", "Quarter: $quarter")
            val achievement = Achievement(dateString, description, points, quarter)
            achievementRepository.addAchievement(achievement)
            Log.d("AddAchievementFragment", "Achievements: ${achievementRepository.getAllAchievements()}")
        }
    }

    private fun getFileName(uri: Uri?): String? {
        if (uri == null) return null

        var fileName: String? = null
        if (uri.scheme == "content") {
            val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val displayNameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (displayNameIndex != -1) {
                        fileName = it.getString(displayNameIndex)
                    }
                }
            }
        }
        if (fileName == null) {
            fileName = uri.lastPathSegment
        }
        return fileName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}