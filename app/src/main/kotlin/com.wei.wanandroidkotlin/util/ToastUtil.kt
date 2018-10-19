package com.wei.wanandroidkotlin.util

import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * @author XiangWei
 * @since 2018/10/19
 */
private object ToastUtil {
    var toast: Toast? = null
    var text: String? = null

    fun show(context: Context, text: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (text.isNullOrEmpty()) {
            return
        }

        if (toast == null || text != this.text) {
            toast = Toast.makeText(context, text, duration)
            this.text = text
        }
        toast?.show()
    }

    fun show(context: Context, @StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
        show(context, ResUtil.getString(id), duration)
    }
}

fun View.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtil.show(context, text, duration)
}

fun Context.toast(vararg texts: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (texts.isEmpty()) return

    var content = String()
    texts.forEach {
        content = content.plus(it)
    }
    ToastUtil.show(this, content, duration)
}

fun Context.toast(@StringRes vararg ids: Int, duration: Int = Toast.LENGTH_SHORT) {
    if (ids.isEmpty()) return

    var content = String()
    ids.forEach {
        content = content.plus(getString(it))
    }
    ToastUtil.show(this, content, duration)
}

fun Fragment.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    context?.let { ToastUtil.show(it, text, duration) }
}

fun Fragment.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    context?.let { ToastUtil.show(it, id, duration) }
}