package com.wei.wanandroidkotlin

import android.content.Context
import android.support.multidex.MultiDexApplication

/**
 * @author XiangWei
 * @since 2018/9/26
 */
class App : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}

lateinit var appContext: Context