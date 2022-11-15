package com.app.habittracker.core.domain.repository

import com.app.habittracker.core.data.Resource
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface IHabitRepository {
    fun getAllHabit() : Flow<Resource<List<Habit>>>
    suspend fun insertHabit(habit: Habit)
    fun updateHabit(habit: Habit)
}