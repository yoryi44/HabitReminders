package com.example.home_data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.home_data.extension.toTimeStamp
import com.example.home_domain.alarm.AlarmHandler
import com.example.home_domain.models.Habit
import java.time.DayOfWeek
import java.time.ZonedDateTime
import javax.inject.Inject

class AlarmHandlerImpl @Inject constructor(
    private val context: Context
) : com.example.home_domain.alarm.AlarmHandler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun setRecurringAlarm(habit: com.example.home_domain.models.Habit) {
        val nextOccurrence = calculateNextOcurrence(habit)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextOccurrence.toTimeStamp(),
            createPendingIntent(habit, nextOccurrence.dayOfWeek)
        )
    }

    private fun createPendingIntent(habit: com.example.home_domain.models.Habit, dayOfWeek: DayOfWeek): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.HABIT_ID, habit.id)
        }
        return PendingIntent.getBroadcast(
            context,
            habit.id.hashCode() * 10 + dayOfWeek.value,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun calculateNextOcurrence(habit: com.example.home_domain.models.Habit): ZonedDateTime {
        val today = ZonedDateTime.now()
        var nextOccurrence = ZonedDateTime.of(today.toLocalDate(), habit.reminder, today.zone)
        if(habit.frequency.contains(today.dayOfWeek) && today.isBefore(nextOccurrence))
        {
            return nextOccurrence
        }

        do{
            nextOccurrence = nextOccurrence.plusDays(1)
        } while (!habit.frequency.contains(nextOccurrence.dayOfWeek))

        return nextOccurrence
    }

    override fun cancel(habit: com.example.home_domain.models.Habit) {
        val nextOccurrence = calculateNextOcurrence(habit)
        val pending = createPendingIntent(habit, nextOccurrence.dayOfWeek)
        alarmManager.cancel(pending)
    }
}