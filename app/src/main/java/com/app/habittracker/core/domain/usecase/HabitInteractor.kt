package com.app.habittracker.core.domain.usecase

import com.app.habittracker.core.data.HabitRepository
import com.app.habittracker.core.data.Resource
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitInteractor @Inject constructor(private val habitRepository: HabitRepository): HabitUseCase
{
    override fun getAllHabit(): Flow<Resource<List<Habit>>> {
        return habitRepository.getAllHabit()
    }

    override fun updateHabit(habit: Habit) {
        habitRepository.updateHabit(habit)
    }

    override suspend fun insertHabit(habit: Habit) {
        habitRepository.insertHabit(habit)
    }

    override suspend fun insertDailyGoal(dailyGoal: DailyGoal) {
        habitRepository.insertDailyGoal(dailyGoal)
    }

}