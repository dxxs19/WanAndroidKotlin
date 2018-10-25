package com.wei.wanandroidkotlin.widgets.video

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView

/**
 * @author XiangWei
 * @since 2018/10/25
 */
class VideoBackgroundView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : VideoView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.getDefaultSize(0, widthMeasureSpec)
        val height = View.getDefaultSize(0, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }
}