package com.app.habittracker.core.data.local.room

import androidx.room.*
import com.app.habittracker.core.data.local.entity.DailyGoalEntity
import com.app.habittracker.core.data.local.entity.HabitEntity
import com.app.habittracker.core.data.local.relations.HabitWithDailyGoals
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Transaction
    @Query("SELECT * FROM habit ORDER BY datetime()")
    fun getAllHabit(): Flow<List<HabitWithDailyGoals>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabits(habit: List<HabitEntity>)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertDailyGoal(dailyGoalEntity: List<DailyGoalEntity>)

    @Update
    fun updateHabit(habit: HabitEntity)


}