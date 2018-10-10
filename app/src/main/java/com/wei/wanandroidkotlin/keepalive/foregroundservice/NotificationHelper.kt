package com.wei.wanandroidkotlin.keepalive.foregroundservice

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat

/**
 * @author XiangWei
 * @since 2018/10/10
 */
object NotificationHelper {

    fun createNotification(context: Context, intent: Intent): Notification {
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder = NotificationCompat.Builder(context, "")
                .setContentTitle("")
                .setContentText("")
                .setTicker("")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setWhen(System.currentTimeMillis())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
        val notification = notificationBuilder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL
        return notification
    }
}