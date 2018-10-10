package com.wei.wanandroidkotlin.keepalive.onepixelactivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

/**
 * @author XiangWei
 * @since 2018/10/9
 */
class KeepAliveManager {

    companion object {

        private const val TAG = "KeepAliveManager"
        private var screenStateReceiver: ScreenStateReceiver? = null

        fun registerBroadCast(context: Context) {
            screenStateReceiver = ScreenStateReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction(Intent.ACTION_SCREEN_ON)
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
            intentFilter.addAction(Intent.ACTION_USER_PRESENT)
            context.registerReceiver(screenStateReceiver, intentFilter)
        }

        fun unRegisterBroadCast(context: Context) {
            screenStateReceiver?.let {
                context.unregisterReceiver(it)
            }
        }
    }

    class ScreenStateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (Intent.ACTION_SCREEN_ON == intent?.action || Intent.ACTION_USER_PRESENT == intent?.action) {
                // 销毁1像素activity
                val action = intent.action
                Log.e(TAG, "收到".plus(action).plus( "广播"))
                context?.sendBroadcast(Intent(OnePixelActivity.ACTION_FINISH))
            } else if (Intent.ACTION_SCREEN_OFF == intent?.action) {
                // 开启1像素activity，提高进程优先级
                Log.e(TAG, "收到熄屏广播")
                val onePixelIntent = Intent(context, OnePixelActivity::class.java)
                onePixelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(onePixelIntent)
            }
        }
    }


}