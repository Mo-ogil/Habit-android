package com.example.habit.common

class HabitList(
    val habits: List<Habit>? = null,
    val type: HabitSection? = null,
) {

}

enum class HabitSection {
    HELPFULL,
    HARMFUL,
    NEUTRAL
}