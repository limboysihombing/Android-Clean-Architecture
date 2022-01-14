package com.app.habittracker.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Habit(
        val id: Int,
        val name: String,
        val icon: String,
        val color: String,
        val habitDay: String,
        val dailyGoals: List<DailyGoal>,
        val endOn: String
): Parcelable
