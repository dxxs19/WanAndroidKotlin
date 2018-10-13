package com.wei.wanandroidkotlin.cache

import android.graphics.Bitmap
import android.util.LruCache

/**
 * @author XiangWei
 * @since 2018/10/13
 */
class CacheHelper {
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    val cacheSize = maxMemory / 8
    var memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String?, value: Bitmap?): Int {
            var size = 0
            value?.let {
                size = value.rowBytes * value.height
            }
            return size
        }
    }

    fun initCache() {

    }

}