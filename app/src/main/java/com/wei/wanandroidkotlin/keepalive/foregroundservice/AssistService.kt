package com.wei.wanandroidkotlin.keepalive.foregroundservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.wei.wanandroidkotlin.activity.MainActivity

class AssistService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NotificationHelper.NOTIFICATION_ID, NotificationHelper.createNotification(this,
                Intent(this, MainActivity::class.java)))
        stopForeground(true)
        return super.onStartCommand(intent, flags, startId)
    }

}
