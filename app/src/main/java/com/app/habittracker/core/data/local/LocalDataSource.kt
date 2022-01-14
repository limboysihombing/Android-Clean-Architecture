package com.app.habittracker.core.data.local

import com.app.habittracker.core.data.local.entity.DailyGoalEntity
import com.app.habittracker.core.data.local.entity.HabitEntity
import com.app.habittracker.core.data.local.relations.HabitWithDailyGoals
import com.app.habittracker.core.data.local.room.HabitDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val habitDao: HabitDao) {
    fun getAllHabit(): Flow<List<HabitEntity>> = habitDao.getAllHabit()

    fun GetAllHabitWithGoals(): Flow<List<HabitWithDailyGoals>> = habitDao.getAllHabitWithDailyGoals()

    suspend fun insertHabits(habitList: List<HabitEntity>) = habitDao.insertHabits(habitList)

    suspend fun insertHabit(habit: HabitEntity) = habitDao.insertHabit(habit)

    suspend fun insertHabitDailyGoal(dailyGoal: DailyGoalEntity) = habitDao.insertDailyGoal(dailyGoal)

    fun updateHabit(habit: HabitEntity){
        habitDao.updateHabit(habit)
    }
}