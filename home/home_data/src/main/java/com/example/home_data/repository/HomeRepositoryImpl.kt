package com.example.home_data.repository

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.home_data.extension.toStartOfDayTimestamp
import com.example.home_data.local.HomeDao
import com.example.home_data.mapper.toDomain
import com.example.home_data.mapper.toDto
import com.example.home_data.mapper.toEntity
import com.example.home_data.mapper.toSyncEntity
import com.example.home_data.remote.HomeApi
import com.example.home_data.remote.util.resultOf
import com.example.home_data.sync.HabitSyncWorker
import com.example.home_domain.alarm.AlarmHandler
import com.example.home_domain.models.Habit
import com.example.home_domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.Duration
import java.time.ZonedDateTime

class HomeRepositoryImpl(
    private val dao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: com.example.home_domain.alarm.AlarmHandler,
    private val workManager: WorkManager
) : com.example.home_domain.repository.HomeRepository {

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<com.example.home_domain.models.Habit>> {
        val localFlow = dao.getAllHabitsForSelectedDays(date.toStartOfDayTimestamp())
            .map { habits -> habits.map { it.toDomain() } }

        val apiFlow = getHabitsFromApi()

        return localFlow.combine(apiFlow) { db, api -> db }
    }

    private fun getHabitsFromApi(): Flow<List<com.example.home_domain.models.Habit>> {
        return flow {

            resultOf {
                val habits = api.getAllHabits().toDomain()
                insertHabits(habits)
            }

            emit(emptyList<com.example.home_domain.models.Habit>())
        }.onStart {
            emit(emptyList())
        }
    }

    override suspend fun insertHabit(habit: com.example.home_domain.models.Habit) {
        handlerAlarm(habit)
        dao.insertHabit(habit.toEntity())
        resultOf {
            api.insertHabit(habit.toDto())
        }.onFailure {
            dao.insertHabitSync(habit.toSyncEntity())
        }
    }

    private suspend fun insertHabits(habits: List<com.example.home_domain.models.Habit>) {
        habits.forEach {
            handlerAlarm(it)
            dao.insertHabit(it.toEntity())
        }
    }

    override suspend fun getHabitById(id: String): com.example.home_domain.models.Habit {
        return dao.getHabitById(id).toDomain()
    }

    private suspend fun handlerAlarm(habit: com.example.home_domain.models.Habit) {
        try {
            val preview = dao.getHabitById(habit.id)
            alarmHandler.cancel(preview.toDomain())
        } catch (_: Exception) { }
        alarmHandler.setRecurringAlarm(habit)
    }

    override suspend fun syncHabits() {
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_habit_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }
}