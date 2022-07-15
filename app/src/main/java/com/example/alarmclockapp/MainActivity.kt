package com.example.alarmclockapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.alarmclockapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {


    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        binding.btnSetAlarmClock.setOnClickListener {
            val materialTimePicker = createMaterialTimePicker()

            materialTimePicker.addOnPositiveButtonClickListener {
                val calendar = makeCalendar(materialTimePicker.minute, materialTimePicker.hour)
                val timeUserSetForAlarmClock = calendar.timeInMillis
                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

                val alarmClockInfo = AlarmManager.AlarmClockInfo(
                    timeUserSetForAlarmClock,
                    makePendingIntentMainActivity()
                )

                // Main fun here ->
// it can launch activity without BroadcastReceiver ->
// alarmManager.setAlarmClock(alarmClockInfo, makeActionPendingIntent())
                alarmManager.setAlarmClock(alarmClockInfo, pendIntentAlarmBrReciever(this))

                Toast.makeText(
                    this,
                    "будильник установлен на ${simpleDateFormat.format(timeUserSetForAlarmClock)}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            materialTimePicker.show(supportFragmentManager, "tag_piker")
        }
        setContentView(binding.root)
    }

    private fun createMaterialTimePicker() = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setHour(12)
        .setMinute(0)
        .setTitleText("Оберіть час для будильника")
        .build()

    @SuppressLint("UnspecifiedImmutableFlag")

    fun makePendingIntentMainActivity(): PendingIntent {
        val alarmMainActivityIntent = nevInstance(this).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        return PendingIntent.getActivity(
            this,
            0,
            alarmMainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    /* //makeActionPendingIntent
    @SuppressLint("UnspecifiedImmutableFlag")
    fun makeActionPendingIntent(): PendingIntent {
        val alarmActionIntent = AlarmClockActivity.nevInstance(this).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        return PendingIntent.getActivity(
            this,
            0,
            alarmActionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

     */

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun pendIntentAlarmBrReciever(context: Context) = PendingIntent.getBroadcast(
        context,
        100,
        AlarmClockBroadcastReciever.nevInstance(context),
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    private fun makeCalendar(minutes: Int, hours: Int) = Calendar.getInstance().apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.MINUTE, minutes)
        set(Calendar.HOUR_OF_DAY, hours)
    }

    companion object {
        fun nevInstance(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}




