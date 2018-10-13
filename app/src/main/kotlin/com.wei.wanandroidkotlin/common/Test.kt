package com.wei.wanandroidkotlin.common


import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache

/**
 * @author XiangWei
 * @since 2018/10/8
 */
class Test {

    internal var maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    internal var cacheSize = maxMemory / 8
    private val mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.rowBytes * value.height / 1024
        }
    }

    private fun test(context: Context) {


    }

    class SCl
}