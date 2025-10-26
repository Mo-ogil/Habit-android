package com.example.habit.common

import java.sql.Date

data class Habit(
    val id: Int? = null, // TODO придумать логику генерации id
    val content: String? = null,
    val dateOfCreating: Date? = null,
    var period: Date? = null,
    var actual: Boolean = true
)