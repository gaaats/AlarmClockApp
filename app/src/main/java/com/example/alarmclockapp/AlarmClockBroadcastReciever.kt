package com.example.alarmclockapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

class AlarmClockBroadcastReciever : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            AlarmClockActivity.nevInstance(it).apply {
                this.flags = FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }
    }

    companion object {
        fun nevInstance(context: Context) = Intent(context, AlarmClockBroadcastReciever::class.java)
    }
}