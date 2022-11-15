package com.app.habittracker.presentation.home.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.habittracker.core.domain.repository.IHabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val habitRepository: IHabitRepository): ViewModel(){
    val habits = habitRepository.getAllHabit().asLiveData()
}