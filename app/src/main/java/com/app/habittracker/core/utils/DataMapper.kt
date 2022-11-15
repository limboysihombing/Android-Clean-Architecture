package com.app.habittracker.core.utils

import com.app.habittracker.core.data.local.entity.DailyGoalEntity
import com.app.habittracker.core.data.local.entity.HabitEntity
import com.app.habittracker.core.data.local.relations.HabitWithDailyGoals
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit

object DataMapper {
    fun mapEntitiesToDomain(input: List<HabitWithDailyGoals>): List<Habit> =
            input.map {
                Habit(
                        id = it.habitEntity.id,
                        name = it.habitEntity.name,
                        icon = it.habitEntity.icon,
                        color = it.habitEntity.color,
                        habitDay = it.habitEntity.habitDays,
                        dailyGoals = mapDailyGoalEntitiesToDailyGoals(it.dailyGoalEntities),
                        endOn = it.habitEntity.endOn
                )
            }

    fun mapDailyGoalEntitiesToDailyGoals(input: List<DailyGoalEntity>): List<DailyGoal> =
            input.map {
                DailyGoal(
                        id = it.id,
                        type = it.type,
                        name = it.name,
                        duration = it.duration,
                        reminder = it.isReminder,
                        doAt = it.doAt
                )
            }

    fun mapDomainToEntity(input: Habit) = HabitEntity(
            id = input.id,
            name = input.name,
            icon = input.icon,
            color = input.color,
            habitDays = input.habitDay,
            endOn = input.endOn
    )
}