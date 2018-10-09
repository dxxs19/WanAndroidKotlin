package com.wei.wanandroidkotlin.activity

import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window

/**
 * @author XiangWei
 * @since 2018/10/9
 */
abstract class BaseActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName
    val context: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFormat(PixelFormat.TRANSPARENT)
        setStatusBar()
        super.onCreate(savedInstanceState)
        setContentView(layoutResId())
        init()
    }

    private fun setStatusBar() {

    }

    abstract fun layoutResId(): Int

    private fun init() {
        initData()
        initNavBar()
        initView()
    }

    abstract fun initData()
    abstract fun initNavBar()
    abstract fun initView()
}