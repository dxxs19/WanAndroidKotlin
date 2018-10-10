package com.wei.wanandroidkotlin.keepalive.onepixelactivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

/**
 * @author XiangWei
 * @since 2018/10/9
 */
class KeepAliveManager {

    companion object {

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
                /// TODO 销毁1像素activity
                KeepAliveActivity.destroyActivity()
            } else if (Intent.ACTION_SCREEN_OFF == intent?.action) {
                /// TODO 开启1像素activity，提高进程优先级
                KeepAliveActivity.showActivity()
            }
        }
    }
}