package com.app.habittracker.core.di

import android.content.Context
import androidx.room.Room
import com.app.habittracker.core.data.local.room.HabitDao
import com.app.habittracker.core.data.local.room.HabitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HabitDatabase = Room.databaseBuilder(
        context,
        HabitDatabase::class.java, "Habit.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideHabitDao(database: HabitDatabase): HabitDao = database.habitDao()
}