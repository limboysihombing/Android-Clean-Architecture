package com.app.habittracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit
import com.app.habittracker.core.domain.usecase.HabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val habitUseCase: HabitUseCase): ViewModel(){
    val habits = habitUseCase.getAllHabit().asLiveData()

    suspend fun insertHabit(habit: Habit){
        habitUseCase.insertHabit(habit)
    }
    suspend fun insertDailyGoal(dailyGoal: DailyGoal){
        habitUseCase.insertDailyGoal(dailyGoal)
    }
}