package com.wei.wanandroidkotlin.common

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @author XiangWei
 * @since 2018/10/8
 */
internal class Test {
    internal class VH private constructor(private val mConvertView: View) : RecyclerView.ViewHolder(mConvertView) {
        private val mViews: SparseArray<View>

        init {
            mViews = SparseArray()
        }

        fun <T : View> getView(id: Int): T {
            var v = mViews.get(id)
            if (v == null) {
                v = mConvertView.findViewById<View>(id)
                mViews.put(id, v)
            }
            return v as T
        }

        fun setText(id: Int, value: String) {
            val view = getView<TextView>(id)
            view.setText(value)
        }

        companion object {

            operator fun get(parent: ViewGroup, layoutId: Int): VH {
                val convertView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
                return VH(convertView)
            }
        }
    }
}