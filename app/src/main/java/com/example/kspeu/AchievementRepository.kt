package com.example.kspeu

import com.example.kspeu.Achievement

class AchievementRepository {
    private val achievements = mutableListOf<Achievement>()



    fun addAchievement(achievement: com.example.kspeu.Achievement) {
        achievements.add(achievement)
    }

    fun getAchievementsByQuarter(quarter: Int): List<Achievement> {
        return achievements.filter { it.quarter == quarter }
    }

    fun getAllAchievements(): List<Achievement> {
        return achievements
    }
}