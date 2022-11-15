package com.app.habittracker.core.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.habittracker.core.data.local.LocalDataSource
import com.app.habittracker.core.data.local.entity.DailyGoalEntity
import com.app.habittracker.core.data.local.entity.HabitEntity
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit
import com.app.habittracker.core.domain.repository.IHabitRepository
import com.app.habittracker.core.utils.AppExecutors
import com.app.habittracker.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutor: AppExecutors
): IHabitRepository {
    override fun getAllHabit(): Flow<Resource<List<Habit>>> =
        object : NetworkBoundResource<List<Habit>, List<HabitEntity>>(){

            @RequiresApi(Build.VERSION_CODES.O)
            override fun loadFromDb(): Flow<List<Habit>> {
                return localDataSource.getAllHabit().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Habit>?): Boolean = false
        }.asFlow()

    override suspend fun insertHabit(habit: Habit) {
        val habitEntity = DataMapper.mapDomainToEntity(habit)
        localDataSource.insertHabit(habitEntity)

        // daily goals
        val dailyGoals = habit.dailyGoals.map {
            DailyGoalEntity(it.id, habitEntity.id, it.type, it.name, it.duration, it.reminder, it.doAt)
        }
        localDataSource.insertDailyGoals(dailyGoals)
        /*appExecutor.diskIO().execute{  }*/
    }

    override fun updateHabit(habit: Habit) {
        TODO("Not yet implemented")
    }

}
