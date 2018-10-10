package com.wei.wanandroidkotlin.keepalive.onepixelactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.wei.wanandroidkotlin.App

class KeepAliveActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        keepAliveActivity = this
        initWindow()
    }

    private fun initWindow() {
        val window = window
        window.setGravity(Gravity.CENTER)
        val layoutParams = window.attributes
        layoutParams.x = 0
        layoutParams.y = 0
        layoutParams.height = 1
        layoutParams.width = 1
        window.attributes = layoutParams
    }

    companion object {
        var keepAliveActivity: KeepAliveActivity? = null
        fun destroyActivity() {
            keepAliveActivity?.finish()
        }

        fun showActivity() {
            val intent = Intent(App.getAppContext(), KeepAliveActivity.javaClass)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            App.getAppContext().startActivity(intent)
        }
    }

    override fun onDestroy() {
        keepAliveActivity = null
        super.onDestroy()
    }
}
