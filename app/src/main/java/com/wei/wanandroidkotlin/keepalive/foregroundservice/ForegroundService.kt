package com.wei.wanandroidkotlin.keepalive.foregroundservice

import android.app.Notification
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.wei.wanandroidkotlin.activity.MainActivity

class ForegroundService : Service() {

    private var foregroundService: ForegroundService? = null
    private var serviceConnection: AssistServiceConnection? = null

    companion object {
        private const val TAG = "ForegroundService"
        val NOTIFICATION_ID = android.os.Process.myPid()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        foregroundService = this
        setForeground()
//        Thread(mRunnable).start()
        return START_STICKY
    }

    private fun setForeground() {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(NOTIFICATION_ID, getNotification())
            return
        }

        if (serviceConnection == null) {
            serviceConnection = AssistServiceConnection()
        }
        bindService(Intent(this, AssistService::class.java), serviceConnection, Service.BIND_AUTO_CREATE)
    }

    inner class AssistServiceConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val assistService = (service as AssistService.LocalBinder).getService()
            foregroundService?.startForeground(NOTIFICATION_ID, getNotification())
            assistService?.startForeground(NOTIFICATION_ID, getNotification())
            assistService?.stopForeground(true)

            foregroundService?.unbindService(serviceConnection)
            serviceConnection = null
        }

    }

    private fun getNotification(): Notification {
        return NotificationHelper.createNotification(this,
                Intent(this, MainActivity::class.java))
    }

    private var mRunnable: Runnable = Runnable {
        while (true) {
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
        super.onDestroy()
    }
}
