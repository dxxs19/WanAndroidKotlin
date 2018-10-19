package com.wei.wanandroidkotlin.util

import android.renderscript.Sampler
import android.util.Log
import com.wei.wanandroidkotlin.test.Sample

/**
 * @author XiangWei
 * @since 2018/10/19
 */
object LogUtil {
    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }
}

fun Sample.log(msg: String) {
    LogUtil.e("Sample", msg)
}