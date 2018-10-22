package com.wei.wanandroidkotlin.widgets.common

import android.view.View

/**
 *  View 属性包装类，目前包装了width及height属性。可以通过属性动画来改变view的大小
 * @author XiangWei
 * @since 2018/10/22
 */
class ViewWrapper {

    private var targetView: View? = null

    constructor(target: View) {
        targetView = target
    }

    fun getWidth(): Int? {
        return targetView?.layoutParams?.width
    }

    fun setWidth(width: Int) {
        targetView?.layoutParams?.width = width
        targetView?.requestLayout()
    }

    fun getHeight(): Int? {
        return targetView?.layoutParams?.height
    }

    fun setHeight(height: Int) {
        targetView?.layoutParams?.height = height
        targetView?.requestLayout()
    }
}