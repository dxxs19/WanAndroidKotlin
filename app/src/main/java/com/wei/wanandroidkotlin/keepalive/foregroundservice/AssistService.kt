package com.wei.wanandroidkotlin.keepalive.foregroundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class AssistService : Service() {

    var assistService: AssistService? = null

    override fun onBind(intent: Intent): IBinder? {
        return LocalBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        assistService = this
        return START_STICKY
    }

    inner class LocalBinder : Binder() {
        fun getService(): AssistService? {
            return assistService
        }
    }
}
