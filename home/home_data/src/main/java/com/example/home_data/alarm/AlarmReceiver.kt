package com.example.home_data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.home_data.extension.goAsync
import com.example.home_domain.alarm.AlarmHandler
import com.example.home_domain.models.Habit
import com.example.home_domain.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val HABIT_ID = "habit_id"
        private const val CHANEL_ID = "habit_id"
    }

    @Inject
    lateinit var repository: com.example.home_domain.repository.HomeRepository

    @Inject
    lateinit var alarmHandler: com.example.home_domain.alarm.AlarmHandler

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        if (context == null || intent == null) return@goAsync
        val id = intent.getStringExtra(HABIT_ID) ?: return@goAsync
        val habit = repository.getHabitById(id)
        createNotificationChanel(context)
        if(!habit.completedDate.contains(LocalDate.now()))
        {
            showNotification(context, habit)
        }
        alarmHandler.setRecurringAlarm(habit)
    }

    private fun showNotification(context: Context, habit: com.example.home_domain.models.Habit) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, CHANEL_ID)
                .setContentTitle(habit.name)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(habit.id.hashCode(),notification)
    }

    private fun createNotificationChanel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(
                CHANEL_ID,
                "Habit Reminder Chanel",
                NotificationManager.IMPORTANCE_HIGH
            )
            chanel.description = "Get your habit reminder!"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(chanel)
        }
    }

}