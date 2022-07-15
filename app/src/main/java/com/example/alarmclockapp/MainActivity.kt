package com.example.alarmclockapp

import android.app.AlarmManager
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmclockapp.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class MainActivity : AppCompatActivity() {


    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnSetAlarmClock.setOnClickListener {
            val materialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Оберіть час для будильника")
                .build()

            materialTimePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.apply {
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    set(Calendar.MINUTE, materialTimePicker.minute)
                    set(Calendar.HOUR_OF_DAY, materialTimePicker.hour)
                }
                val timeUserSetForAlarmClock = calendar.timeInMillis

                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


            }
            materialTimePicker.show(supportFragmentManager, "tag_piker")
        }

        setContentView(binding.root)
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}



