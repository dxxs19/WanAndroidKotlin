package com.wei.wanandroidkotlin.net.response

import android.util.Log

/**
 * @author XiangWei
 * @since 2018/9/29
 */
class Translation1 {
    private val status: Int = 0
    private val content: Content? = null

    private class Content {
        private val from: String? = null
        private val to: String? = null
        private val vendor: String? = null
        private val out: String? = null
        private val errNo: Int = 0

        fun getOut() : String {
            return out.toString()
        }
    }

    //定义 输出返回数据 的方法
    fun show() {

        Log.d("RxJava", "翻译内容 = " + content?.getOut())

    }
}
