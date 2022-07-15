package com.example.alarmclockapp

import android.app.AlarmManager
import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.example.alarmclockapp.databinding.ActivityAlarmClockBinding
import com.example.alarmclockapp.databinding.ActivityMainBinding

class AlarmClockActivity : AppCompatActivity() {

    private var _binding: ActivityAlarmClockBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private val currentRingtonAlarm by lazy {
        RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
    }

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAlarmClockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wakeUpScreen()
//        currentRingtonAlarm.play()


        binding.btnDisableAlarmClock.setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    private fun wakeUpScreen() {
        this.setShowWhenLocked(true)
        setTurnScreenOn(true)
        val keyGuardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        keyGuardManager.requestDismissKeyguard(this, null)
    }


    override fun onDestroy() {
//        currentRingtonAlarm.stop()
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun nevInstance(context: Context) = Intent(context, AlarmClockActivity::class.java)
    }
}