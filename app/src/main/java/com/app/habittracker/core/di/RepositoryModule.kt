package com.app.habittracker.core.di

import com.app.habittracker.core.data.HabitRepository
import com.app.habittracker.core.domain.repository.IHabitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(habitRepository: HabitRepository): IHabitRepository
}