package com.wei.wanandroidkotlin.util

import android.content.Context
import com.wei.wanandroidkotlin.appContext

object UIUtil {

    private const val HALF_VAL = 0.5f

    // 获取状态栏高度(未经缩放处理的原始px)
    val statusBarHeight: Int by lazy {
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c!!.newInstance()
            val field = c.getField("status_bar_height")
            val id = Integer.parseInt(field!!.get(obj).toString())
            context.resources.getDimensionPixelSize(id)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    private val context: Context by lazy { appContext }

    fun dpToPx(dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + HALF_VAL).toInt()
    }

    fun dpToPx(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + HALF_VAL).toInt()
    }

    fun pxToDp(pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + HALF_VAL).toInt() + 1
    }

//    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
//    fun setOverScrollNever(lv: ListView) {
//        if (DeviceUtil.brand == Constants.BRAND_MEIZU) {
//            // 取消魅族的下拉悬停功能
//            lv.overScrollMode = View.OVER_SCROLL_NEVER
//        }
//    }
}


