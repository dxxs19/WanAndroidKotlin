package com.wei.wanandroidkotlin.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import com.wei.wanandroidkotlin.Constants
import com.wei.wanandroidkotlin.util.UIUtil


class SideBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private val COLOR_NORMAL = Color.parseColor("#616161")
        private val COLOR_FOCUS = Color.parseColor("#F88701")
        private const val TEXT_SIZE_DP = 11
        private val SELECTIONS = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

    }

    @ColorInt
    var colorNormal = COLOR_NORMAL // 画笔颜色

    @ColorInt
    private var colorFocus = COLOR_FOCUS

    private var listener: OnTouchLetterListener? = null

    private val paint = Paint()
    private var choose = Constants.ERR_NOT_FOUND

    // 每个字母的高度
    private var letterHeight: Int = 0
    private var realHeight: Int = 0

    var textSize: Int = 0

    private var customLetterHeight: Boolean = false

    var gravity = Gravity.TOP

    // 准备好的A~Z的字母数组
    var selections: Array<String> = SELECTIONS

    private var firstDraw = true

    init {
        textSize = UIUtil.dpToPx(TEXT_SIZE_DP)
    }

    fun setData(data: Array<String>?) {
        selections = data ?: SELECTIONS

        if (!customLetterHeight) {
            letterHeight = height / selections.size
        }
        computeRealHeight()

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val half = height / 2

        val locationArray = IntArray(2)
        if (firstDraw) {
            setData(null)
            firstDraw = false
            getLocationOnScreen(locationArray)
            Log.e(javaClass.simpleName, locationArray[1].toString())
        }

        var offset = 0
        when (gravity) {
            Gravity.TOP -> {
                // do nothing
            }
            Gravity.CENTER_VERTICAL, Gravity.CENTER -> {
                offset = half - realHeight / 2
            }
        }

        // 画字母
        for (i in selections.indices) {
            paint.color = colorNormal

            // 设置字体格式
            paint.typeface = Typeface.DEFAULT_BOLD
            paint.isAntiAlias = true
            paint.textSize = textSize.toFloat()

            // 如果这一项被选中，则换一种颜色画
            if (i == choose) {
                paint.color = colorFocus
                paint.isFakeBoldText = true
            }
            // 要画的字母的x,y坐标
            val posX = width / 2f
            var posY = (i * letterHeight + letterHeight / 2 + offset).toFloat()

            // 画出字母
//            DrawUtil.drawTextByAlignX(canvas, selections[i], posX, posY, paint, Align.CENTER)
            canvas.drawText(selections[i], posX, posY, paint)

            // 重新设置画笔
            paint.reset()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var y = event.y

        val half = height / 2

        var offset: Int
        when (gravity) {
            Gravity.TOP -> {
                // do nothing
                y -= letterHeight / 2f
            }
            Gravity.CENTER_VERTICAL, Gravity.CENTER -> {
                offset = half - realHeight / 2 + letterHeight / 2
                y -= offset.toFloat()
            }
        }

        // 算出点击的字母的索引
        val index = (y / letterHeight).toInt()

        // 保存上次点击的字母的索引到oldChoose
        val oldChoose = choose
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                // view有字母部分的高度，其他部分不接受touch事件
                if (y > realHeight) {
                    return false
                }
                // showBg = true;
                if (oldChoose != index && index >= 0 && index < selections.size) {
                    choose = index
                    invalidate()

                    listener?.onTouchLetterChanged(index, selections[index], true)
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (oldChoose != index && index >= 0 && index < selections.size) {
                    choose = index
                    invalidate()

                    listener?.onTouchLetterChanged(index, selections[index], true)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                choose = -1
                invalidate()

                if (index <= 0) {
                    listener?.onTouchLetterChanged(0, selections[0], false)
                } else if (index >= 0 && index < selections.size) {
                    listener?.onTouchLetterChanged(index, selections[index], false)
                } else if (index >= selections.size) {
                    listener?.onTouchLetterChanged(selections.size - 1, selections[selections.size - 1], false)
                }
            }
        }

        return true
    }

    /**
     * 回调方法，注册监听器
     *
     * @param listener
     */
    fun setOnTouchLetterChangeListener(listener: OnTouchLetterListener) {
        this.listener = listener
    }

    /**
     * SideBar 的监听器接口
     */
    interface OnTouchLetterListener {
        fun onTouchLetterChanged(index: Int, s: String, isFocus: Boolean)
    }

    /**
     * 设置每个字母的高度
     *
     * @param singleHeight
     */
    fun setSingleHeight(singleHeight: Int) {
        customLetterHeight = true
        letterHeight = singleHeight
    }

    private fun computeRealHeight() {
        realHeight = letterHeight * selections.size
    }
}