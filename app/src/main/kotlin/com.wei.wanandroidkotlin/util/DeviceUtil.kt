package com.wei.wanandroidkotlin.util

import android.os.Build

/**
 * @author XiangWei
 * @since 2018/9/26
 */
object DeviceUtil {

    /**
     *  获取当前SDK的版本号
     */
    val sdkVersion: Int
        get() = Build.VERSION.SDK_INT

    val brand: String
        get() = Build.BRAND

    val mobileType: String
        get() = Build.MODEL.replace(" ", "")

}