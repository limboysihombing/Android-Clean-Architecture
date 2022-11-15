package com.app.habittracker.core.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.app.habittracker.core.data.local.entity.DailyGoalEntity
import com.app.habittracker.core.data.local.entity.HabitEntity

data class HabitWithDailyGoals (
    @Embedded val habitEntity: HabitEntity,
    @Relation(
            parentColumn = "id",
            entityColumn = "habitEntityId"
    )
    val dailyGoalEntities: List<DailyGoalEntity>
)