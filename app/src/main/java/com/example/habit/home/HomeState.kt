package com.example.habit.home

import com.example.habit.common.HabitList
import com.example.habit.common.HabitSection

data class HomeState (
    val sections: List<HabitSection> = emptyList<HabitSection>(),
    val selectedSection: HabitSection = HabitSection.HELPFULL,
    val habitList: HabitList? = null,
    val isCreatingHabit: Boolean = false,
    val onError: Boolean = false,
)