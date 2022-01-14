package com.app.habittracker.core.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "Habit")
data class HabitEntity(
        @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

        @ColumnInfo(name = "name")
    var name: String,

        @ColumnInfo(name ="icon")
    var icon: String,

        @ColumnInfo(name ="color")
    var color: String,

        @ColumnInfo(name = "habitDay")
    var habitDays: String,

        @ColumnInfo(name ="endOn")
    var endOn : String
)

