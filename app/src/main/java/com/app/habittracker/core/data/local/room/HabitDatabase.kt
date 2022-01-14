package com.app.habittracker.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.habittracker.core.data.local.entity.DailyGoalEntity
import com.app.habittracker.core.data.local.entity.HabitEntity

@Database(entities = [HabitEntity::class, DailyGoalEntity::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}