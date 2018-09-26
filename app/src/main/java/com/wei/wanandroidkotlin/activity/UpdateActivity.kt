package com.wei.wanandroidkotlin.activity

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.wei.wanandroidkotlin.R
import com.wei.wanandroidkotlin.widgets.wechatrefresh.CustomProgressDrawable
import com.wei.wanandroidkotlin.widgets.wechatrefresh.CustomSwipeRefreshLayout

/**
 * @author XiangWei
 * @since 2018/9/7
 */
class UpdateActivity : AppCompatActivity() {

    lateinit var listView: ListView
    //    lateinit var layout: PullToRefreshLayout
    private var datas = arrayOfNulls<String>(100)
    private var mRefreshLayout: CustomSwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        initView()
    }

    private fun initView() {
        listView = findViewById(R.id.listView)
//        layout = findViewById(R.id.refresh_layout)
        initDatas()
        initHeader()
    }

    private fun initHeader() {
        val head = LayoutInflater.from(this).inflate(R.layout.head_view_layout, null)
        listView.addHeaderView(head)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, datas)
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        val drawable = CustomProgressDrawable(this, mRefreshLayout)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.loading)
        drawable.setBitmap(bitmap)
        mRefreshLayout?.setProgressView(drawable)
        mRefreshLayout?.setBackgroundColor(Color.BLACK)
        mRefreshLayout?.setProgressBackgroundColorSchemeColor(Color.BLACK)
        mRefreshLayout?.setOnRefreshListener {
            //                mPresenter.loadData(1)
            val handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    mRefreshLayout?.isRefreshing = false
                    Toast.makeText(
                            this@UpdateActivity,
                            "刷新完成",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
            Thread(Runnable {
                try {
                    Thread.sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                handler.sendEmptyMessage(0)
            }).start()
        }
    }

    private fun initDatas() {
        for (i in 0 until 100) {
            datas[i] = "item => $i"
        }
    }


}