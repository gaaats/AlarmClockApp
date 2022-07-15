package com.example.alarmclockapp

import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmclockapp.databinding.ActivityAlarmClockBinding
import com.example.alarmclockapp.databinding.ActivityMainBinding

class AlarmClockActivity : AppCompatActivity() {

    private var _binding: ActivityAlarmClockBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private val currentRingtonAlarm by lazy {
        RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAlarmClockBinding.inflate(layoutInflater)

        currentRingtonAlarm.play()
        setContentView(binding.root)

        binding.btnDisableAlarmClock.setOnClickListener {
            currentRingtonAlarm.stop()
        }
    }


    override fun onDestroy() {
        currentRingtonAlarm.stop()
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun nevInstance(context: Context) = Intent(context, AlarmClockActivity::class.java)
    }
}