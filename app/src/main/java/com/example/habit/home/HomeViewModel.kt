package com.example.habit.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habit.common.Habit
import com.example.habit.common.HabitSection
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    //private val habitsRepository: HabitsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<HomeUiEvent>()

    init {
        viewModelScope.launch {
            _events.collect { event ->
                handleEvent(event)
            }
        }
    }

    fun onEvent(event: HomeUiEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    private suspend fun handleEvent(event: HomeUiEvent) {
        when (event) {

            is HomeUiEvent.onChangeSelection -> TODO()
            is HomeUiEvent.onCreateHabit -> TODO()
            is HomeUiEvent.onDeleteHabit -> TODO()
            is HomeUiEvent.onLoadList -> TODO()
        }
    }

    private suspend fun loadHabits() {
        _state.update {
            it.copy(onError = false)
        }
        //TODO доделать логику (для этого нужно сделать репозитории):

//        try {
//            val habits = habitsRepository.getHabits()
//            val sections = habitsRepository.getSections()
//            _state.update {
//                it.copy(
//                    habits = habits,
//                    sections = sections,
//                )
//            }
//        } catch (e: Exception) {
//            _state.update {
//                it.copy(
//                    error = "Не удалось загрузить привычки: ${e.message}",
//                )
//            }
//        }
//    }
//
//    loadHabits()
    }

    private suspend fun change(habitId: String) {
//        try {
//            val updatedHabit = habitsRepository.toggleHabitCompletion(habitId)
//            _state.update { state ->
//                state.copy(
//                    habits = state.habits.map { habit ->
//                        if (habit.id == habitId) updatedHabit else habit
//                    }
//                )
//            }
//        } catch (e: Exception) {
//            _state.update {
//                it.copy(error = "Не удалось обновить привычку: ${e.message}")
//            }
//        }
    }

    private suspend fun deleteHabit(habitId: Int?) {
//        try {
//            habitsRepository.deleteHabit(habitId)
//            _state.update { state ->
//                state.copy(habits = state.habits.filter { it.id != habitId })
//            }
//        } catch (e: Exception) {
//            _state.update {
//                it.copy(error = "Не удалось удалить привычку: ${e.message}")
//            }
//        }
    }

    private suspend fun onCreatedHabit(habit: Habit) {
//        try {
//            val savedHabit = habitsRepository.saveHabit(habit)
//            _state.update { state ->
//                state.copy(
//                    habits = state.habits + savedHabit,
//                    isCreatingHabit = false
//                )
//            }
//        } catch (e: Exception) {
//            _state.update {
//                it.copy(error = "Не удалось сохранить привычку: ${e.message}")
//            }
//        }
    }

    private suspend fun cancelCreatingHabit() {
        _state.update { it.copy(isCreatingHabit = false) }
    }

    private suspend fun selectSection(section: HabitSection?) {
        _state.update { it.copy(selectedSection = section!!) }
    }

    private suspend fun dismissError() {
        _state.update { it.copy(onError = false) }
    }
}