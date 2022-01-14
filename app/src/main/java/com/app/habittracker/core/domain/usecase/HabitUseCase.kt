package com.app.habittracker.core.domain.usecase

import com.app.habittracker.core.data.Resource
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitUseCase {
    fun getAllHabit(): Flow<Resource<List<Habit>>>
    fun updateHabit(habit: Habit)
    suspend fun insertHabit(habit: Habit)
    suspend fun insertDailyGoal(dailyGoal : DailyGoal)
}