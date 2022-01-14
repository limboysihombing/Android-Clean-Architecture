package com.app.habittracker.core.data.local.room

import androidx.room.*
import com.app.habittracker.core.data.local.entity.DailyGoalEntity
import com.app.habittracker.core.data.local.entity.HabitEntity
import com.app.habittracker.core.data.local.relations.HabitWithDailyGoals
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit order by name asc")
    fun getAllHabit(): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabits(habit: List<HabitEntity>)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertDailyGoal(dailyGoalEntity: DailyGoalEntity)

    @Update
    fun updateHabit(habit: HabitEntity)

    @Transaction
    @Query("SELECT * FROM habit ORDER BY datetime()")
    fun getAllHabitWithDailyGoals(): Flow<List<HabitWithDailyGoals>>
}