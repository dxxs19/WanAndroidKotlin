package com.wei.wanandroidkotlin.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.wei.wanandroidkotlin.R
import com.wei.wanandroidkotlin.activity.softinput.SoftInputModeActivity
import com.wei.wanandroidkotlin.common.QuickAdapter
import com.wei.wanandroidkotlin.keepalive.foregroundservice.ForegroundService
import com.wei.wanandroidkotlin.keepalive.onepixelactivity.KeepAliveManager
import com.wei.wanandroidkotlin.model.ButtonBean
import com.wei.wanandroidkotlin.net.GetRequestApi
import com.wei.wanandroidkotlin.net.response.Translation1
import com.wei.wanandroidkotlin.net.response.Translation2
import com.wei.wanandroidkotlin.net.retrofit.RetrofitHelper
import com.wei.wanandroidkotlin.rx.RxBus
import com.wei.wanandroidkotlin.rx.RxOperators
import com.wei.wanandroidkotlin.widgets.imagedrag.PostImagesActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.nereo.multi_image_selector.MultiImageSelector
import me.nereo.multi_image_selector.MultiImageSelectorActivity
import java.util.*


class MainActivity : BaseActivity() {
    private val REQUEST_IMAGE = 1001

    override fun layoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        initButtons()
        textRxBus()
        window.decorView.postDelayed({ test() }, 100L)
    }

    override fun initNavBar() {
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttons: java.util.ArrayList<ButtonBean>
    private val num: Int
        get() = 10
    private var serviceIntent: Intent? = null

    private fun test() {
//        RxJavaOperators.testFilter()
//        RxJavaOperators.testZip()
//        RxJavaOperators.testFlowable()
//        RxJavaOperators.testInterval()
//        RxOperators().testFlatMap()
//        AOPTest.login("xxxx", "123")
//        AOPTest.computePlus(10, 20000000)
//        RxJavaOperators.testFlatMap()
//        RxJavaOperators.testCreate()
        val rxKotlin = RxOperators()
        rxKotlin.testCreate()
//        rxKotlin.testBuffer()
//        testFlatMap()
//        rxKotlin.testCombine()
//        rxKotlin.testFilter()
//        rxKotlin.testBoolean()
    }

    lateinit var observable1: Observable<Translation1>
    lateinit var observable2: Observable<Translation2>
    private fun testFlatMap() {
        val requestApi = RetrofitHelper.getRetrofit("http://fy.iciba.com/").create(GetRequestApi::class.java)
        observable1 = requestApi.call
        observable2 = requestApi.call_2
        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { t ->
                    Log.d(TAG, "第1次网络请求成功")
                    t.show()
                }
                .observeOn(Schedulers.io())
                .flatMap {
                    // 将网络请求1转换成网络请求2，即发送网络请求2
                    observable2
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(io.reactivex.functions.Consumer<Translation2> { result ->
                    Log.d(TAG, "第2次网络请求成功")
                    result.show()
                }, io.reactivex.functions.Consumer<Throwable> {
                    Log.d(TAG, "第2次网络请求失败")
                })
    }

    private fun textRxBus() {
        RxBus.get().accept(100)
                .subscribe {
                    Log.e(TAG, it.toString())
                }

        window.decorView.postDelayed({
            Log.e(TAG, "发送")
            RxBus.get().post(100)
        }, 1000)

    }

    override fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        adapter.setOnClickListener(object : QuickAdapter.OnClickListener<ButtonBean> {
            override fun onClick(v: View, t: ButtonBean) {
                clickListener(t.type)
            }
        })
//        recyclerView.addItemDecoration()
        recyclerView.itemAnimator = DefaultItemAnimator()

        KeepAliveManager.registerBroadCast(this)
    }

    override fun onDestroy() {
        KeepAliveManager.unRegisterBroadCast(this)
        super.onDestroy()
    }

    private val adapter by lazy {
        object : QuickAdapter<ButtonBean>(buttons) {

            override fun getLayoutId(viewType: Int): Int {
                return R.layout.item_button
            }

            override fun convert(holder: VH, data: ButtonBean, position: Int) {
                holder.setButtonTxt(R.id.btn, data.text)
            }

        }
    }

    private fun initButtons() {
        buttons = ArrayList()
        buttons.add(ButtonBean(1, "SoftInputMode软键盘适配"))
        buttons.add(ButtonBean(2, "Context的几种应用及区别"))
        buttons.add(ButtonBean(3, "Android版本及对应的Api"))
        buttons.add(ButtonBean(4, "仿微信拖动图片到底部删除"))
        /// TODO 这里加相应的按钮
    }

    fun clickListener(id: Int) {
        when (id) {
            1 -> testSoftInputMode()
            2 -> testContext()
            3 -> testVersionApi()
            4 -> selectPics()
            /// TODO 这里对相应按钮的点击事件做处理
        }
    }

    private fun testVersionApi() {

    }

    private fun testContext() {

    }

    private fun testSoftInputMode() {
        startActivity(Intent(context, SoftInputModeActivity::class.java))
    }

    override fun onPause() {
        serviceIntent = Intent(this, ForegroundService::class.java)
        startService(serviceIntent)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        serviceIntent?.let { stopService(serviceIntent) }
    }

    private fun selectPics() {
        MultiImageSelector.create()
                .showCamera(true) // show camera or not. true by default
                .count(9) // max select image size, 9 by default. used width #.multi()
                .multi() // multi mode, default mode;
                .start(this@MainActivity, REQUEST_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {//文章图片
            data?.let {
                PostImagesActivity.startPostActivity(this@MainActivity,
                        data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT))
            }
        }
    }
}

