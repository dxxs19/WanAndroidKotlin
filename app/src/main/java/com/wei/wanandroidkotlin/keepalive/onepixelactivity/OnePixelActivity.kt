package com.wei.wanandroidkotlin.keepalive.onepixelactivity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PowerManager
import android.util.Log

class OnePixelActivity : Activity() {

    companion object {
        private const val TAG = "OnePixelActivity"
        const val ACTION_FINISH = "com.wei.action.finish_activity"
    }

    private val finishReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            finish()
            Log.e(TAG, "收到关闭activity广播，已经销毁1像素activity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
        registerReceiver(finishReceiver, IntentFilter(ACTION_FINISH))
        checkScreenState()
    }

    private fun initWindow() {
        val layoutParams = window.attributes
        layoutParams.x = 0
        layoutParams.y = 0
        layoutParams.height = 1
        layoutParams.width = 1
        window.attributes = layoutParams
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "1像素activity创建成功")
        checkScreenState()
    }

    private fun checkScreenState() {
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (pm.isScreenOn) {
//            finish()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(finishReceiver)
        super.onDestroy()
    }
}
