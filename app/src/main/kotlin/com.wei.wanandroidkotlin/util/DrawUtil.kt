package com.wei.wanandroidkotlin.util

import android.graphics.*
import android.graphics.Paint.Align
import android.os.Build

object DrawUtil {

    /**
     * 根据X方向绘制文本
     */
    fun drawTextByAlignX(canvas: Canvas, text: String, x: Float, y: Float, paint: Paint, align: Align) {
        val newY = y + paint.textSize
        when (align) {
            Paint.Align.LEFT -> drawTextInLeftX(canvas, text, x, newY, paint)
            Paint.Align.CENTER -> drawTextInCenterX(canvas, text, x, newY, paint)
            Paint.Align.RIGHT -> drawTextInRightX(canvas, text, x, newY, paint)
            else -> {
            }
        }
    }

    private fun drawTextInLeftX(canvas: Canvas, text: String, x: Float, y: Float, paint: Paint) {
        canvas.drawText(text, x, y, paint)
    }

    private fun drawTextInCenterX(canvas: Canvas, text: String, x: Float, y: Float, paint: Paint) {
        canvas.drawText(text, x - paint.measureText(text) / 2, y, paint)
    }

    private fun drawTextInRightX(canvas: Canvas, text: String, x: Float, y: Float, paint: Paint) {
        canvas.drawText(text, x - paint.measureText(text), y, paint)
    }

    /**
     * 根据中心点绘制文本
     */
    fun drawTextInCenterXY(canvas: Canvas, text: String, x: Float, y: Float, p: Paint) {
        val halfSize = p.measureText(text) / 2
        canvas.drawText(text, x - halfSize, y + halfSize, p)
    }

    /**
     * 只调整X的对齐方式, Y不变
     */
    fun drawBmpByAlignX(canvas: Canvas, bmp: Bitmap, x: Float, y: Float, paint: Paint, align: Align) {
        when (align) {
            Paint.Align.LEFT -> drawBmpInLeftX(canvas, bmp, x, y, paint)
            Paint.Align.CENTER -> drawBmpInCenterX(canvas, bmp, x, y, paint)
            Paint.Align.RIGHT -> drawBmpInRightX(canvas, bmp, x, y, paint)
            else -> {
            }
        }
    }

    fun drawBmpInCenterX(canvas: Canvas, bmp: Bitmap, x: Float, y: Float, paint: Paint) {
        canvas.drawBitmap(bmp, x - bmp.width / 2f, y, paint)
    }

    private fun drawBmpInLeftX(canvas: Canvas, bmp: Bitmap, x: Float, y: Float, paint: Paint) {
        canvas.drawBitmap(bmp, x, y, paint)
    }

    private fun drawBmpInRightX(canvas: Canvas, bmp: Bitmap, x: Float, y: Float, paint: Paint) {
        canvas.drawBitmap(bmp, x - bmp.width, y, paint)
    }

    /**
     * 把图片缩放后居中画在指定点, X和Y都居中
     *
     * @param bmp
     * @param canvas
     * @param x
     * @param y
     * @param paint
     * @param scaleW 1.0f表示不缩放
     * @param scaleH 1.0f表示不缩放
     */
    fun drawBmpScaleInCenter(canvas: Canvas, bmp: Bitmap, x: Float, y: Float, paint: Paint, scaleW: Float, scaleH: Float) {
        val m = Matrix()
        m.setScale(scaleW, scaleH)
        val newWidth = bmp.width * scaleW
        val newHeight = bmp.height * scaleH
        m.postTranslate(x - newWidth / 2, y - newHeight / 2)
        canvas.drawBitmap(bmp, m, paint)
    }

    /**
     * 在canvas上绘制圆角区域, 兼容旧版本
     *
     * @param canvas
     * @param paint
     * @param radius 半径
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    fun drawRoundRect(canvas: Canvas, paint: Paint, radius: Float, left: Float, top: Float, right: Float, bottom: Float) {
        val r = RectF(left, top, right, bottom)
        drawRoundRect(canvas, paint, radius, r)
    }

    /**
     * 在canvas上绘制圆角区域, 兼容旧版本
     *
     * @param canvas
     * @param paint
     * @param radius
     * @param r
     */
    fun drawRoundRect(canvas: Canvas, paint: Paint, radius: Float, r: RectF) {
        if (DeviceUtil.sdkVersion < Build.VERSION_CODES.LOLLIPOP) {
            val path = Path()
            path.addRoundRect(r, radius, radius, Path.Direction.CW)
            canvas.drawPath(path, paint)
        } else {
            canvas.drawRoundRect(r, radius, radius, paint)
        }
    }

}
