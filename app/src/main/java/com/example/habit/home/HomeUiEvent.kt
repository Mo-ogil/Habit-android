package com.example.habit.home

import com.example.habit.common.Habit
import com.example.habit.common.HabitList
import com.example.habit.common.HabitSection

sealed interface HomeUiEvent {
    data class onLoadList(val section: HabitSection): HomeUiEvent
    data class onCreateHabit(val habit: Habit, val habitList: HabitList) : HomeUiEvent
    data class onChangeSelection(val habitSection: HabitSection) : HomeUiEvent
    data class onDeleteHabit(val habit: Habit) : HomeUiEvent
}
