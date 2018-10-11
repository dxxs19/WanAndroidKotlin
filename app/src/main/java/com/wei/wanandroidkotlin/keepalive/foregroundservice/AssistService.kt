package com.wei.wanandroidkotlin.keepalive.foregroundservice

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AssistService : Service() {

    companion object {
        private const val TAG = "AssistService"
    }

    override fun onCreate() {
        Log.e(TAG, "--- onCreate ---")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        startForeground(NotificationHelper.NOTIFICATION_ID, NotificationHelper.createNotification(this,
//                Intent(this, MainActivity::class.java)))
        startForeground(NotificationHelper.NOTIFICATION_ID, Notification())
        stopForeground(true)
        stopSelf()
        Log.e(TAG, "--- onStartCommand ---")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.e(TAG, "--- onDestroy ---")
        super.onDestroy()
    }

}
