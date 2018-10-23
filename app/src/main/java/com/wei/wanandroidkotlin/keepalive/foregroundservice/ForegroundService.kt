package com.wei.wanandroidkotlin.keepalive.foregroundservice

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.wei.wanandroidkotlin.activity.MainActivity

class ForegroundService : Service() {

    companion object {
        private const val TAG = "ForegroundService"
    }

    private var thread: Thread? = null
    private var stopThread: Boolean? = false

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "--- onCreate ---")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        setForeground()
        // 测试服务是否运行
//        thread = thread ?: Thread(mRunnable)
//        thread?.let {
//            if (!it.isAlive) {
//                it.start()
//            }
//        }
        Log.e(TAG, "--- onStartCommand ---")
        return START_STICKY
    }

    private fun setForeground() {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(NotificationHelper.NOTIFICATION_ID, getNotification())
        } else {
            startService(Intent(this, AssistService::class.java))
            startForeground(NotificationHelper.NOTIFICATION_ID, getNotification())
        }
    }

    private fun getNotification(): Notification {
//        return NotificationHelper.createNotification(this,
//                Intent(this, MainActivity::class.java))
        return Notification()
    }

    private var mRunnable: Runnable = Runnable {
        while (stopThread == false) {
            Log.e(TAG, "" + System.currentTimeMillis())
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }

    override fun onDestroy() {
        stopForeground(true)
        Log.e(TAG, "--- onDestroy ---")
        stopThread = true
        super.onDestroy()
    }
}
