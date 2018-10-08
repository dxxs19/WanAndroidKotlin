package com.wei.wanandroidkotlin.common

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

/**
 * @author XiangWei
 * @since 2018/10/8
 */
abstract class QuickAdapter<T> constructor(private val datas: List<T>) : RecyclerView.Adapter<QuickAdapter.VH>() {

    abstract fun getLayoutId(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.get(parent, getLayoutId(viewType))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        convert(holder, datas[position], position)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    abstract fun convert(holder: VH, data: T, position: Int)


    class VH constructor(private val contentView: View) : RecyclerView.ViewHolder(contentView) {
        private val views: SparseArray<View> = SparseArray()

        companion object {
            fun get(parent: ViewGroup, layoutId: Int): VH {
                val convertView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
                return VH(convertView)
            }
        }

        private fun <T : View> getView(id: Int): T {
            var v = views.get(id)
            if (v == null) {
                v = contentView.findViewById(id)
                views.put(id, v)
            }
            return v as T
        }

        fun setText(id: Int, value: String) {
            val view = getView<View>(id)
            if (view is TextView) {
                view.text = value
            }
        }

        fun setButtonTxt(id: Int, value: String) {
            val view = getView<View>(id)
            if (view is Button) {
                view.text = value
            }
        }
    }

}
