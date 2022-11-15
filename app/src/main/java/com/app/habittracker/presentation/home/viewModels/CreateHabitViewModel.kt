package com.app.habittracker.presentation.home.viewModels

import androidx.lifecycle.ViewModel
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit
import com.app.habittracker.core.domain.repository.IHabitRepository
import com.app.habittracker.utils.SingleLiveEvent
import com.app.habittracker.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class CreateHabitViewModel @Inject constructor(
    private val habitRepository: IHabitRepository
): ViewModel() {

    var name = SingleLiveEvent<String>()
    var habitDays = SingleLiveEvent<ArrayList<Int>>()
    var dailyGoalRes = SingleLiveEvent<String>() //"Off"
    var duration = SingleLiveEvent<Int>() //15
    var repeatCount = SingleLiveEvent<Int>() //2
    var doAt = SingleLiveEvent<String>()
    var endAt = SingleLiveEvent<String>() //""


    init {
        name.value = ""
        habitDays.value = arrayListOf(1, 1, 1, 1, 1, 1, 1)
        dailyGoalRes.value = "Off"
        duration.value = 15
        repeatCount.value = 2
        doAt.value = Utility.getCurrentTime()
    }

    fun updateHabitDays(index: Int, value: Int) {
        habitDays.value?.set(index, value)
    }

    suspend fun insertHabit(habit: Habit){
        habitRepository.insertHabit(habit)
    }

    fun List<Int>.concat() = this.joinToString("") { it.toString() }.takeWhile { it.isDigit() }
}