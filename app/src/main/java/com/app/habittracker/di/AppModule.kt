package com.app.habittracker.di

import com.app.habittracker.core.domain.usecase.HabitInteractor
import com.app.habittracker.core.domain.usecase.HabitUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideHabitUseCase(habitInteractor: HabitInteractor): HabitUseCase
}