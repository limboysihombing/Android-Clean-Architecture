package com.app.habittracker.core.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime
import java.util.*

@Parcelize
data class DailyGoal(
        val id : Int,
        val habitId: Int,
        val type: String,
        val name: String,
        val duration: Int,
        val reminder: Boolean,
        val doAt: String


): Parcelable
