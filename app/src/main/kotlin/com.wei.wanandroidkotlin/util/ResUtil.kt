package com.wei.wanandroidkotlin.util

import android.content.Context
import android.support.annotation.StringRes
import com.wei.wanandroidkotlin.appContext

/**
 * @author XiangWei
 * @since 2018/10/19
 */
object ResUtil {

    private val context: Context
        get() = appContext

    fun getString(@StringRes id: Int): String = context.getString(id)
}