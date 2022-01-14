package com.app.habittracker.core.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "HabitDailyGoal")
data class DailyGoalEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    var id:Int,

    @ColumnInfo(name="habitEntityId")
    var habitEntityId : Int,

    @ColumnInfo(name="type")
    var type : String,

    @ColumnInfo(name="name")
    var name : String,

    @ColumnInfo(name="duration")
    var duration : Int,

    @ColumnInfo(name ="reminder")
    var isReminder: Boolean,

    @ColumnInfo(name="doAt")
    var doAt : String,
)
