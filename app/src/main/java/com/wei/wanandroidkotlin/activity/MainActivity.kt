package com.wei.wanandroidkotlin.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.wei.wanandroidkotlin.R
import com.wei.wanandroidkotlin.common.QuickAdapter
import com.wei.wanandroidkotlin.model.ButtonBean
import com.wei.wanandroidkotlin.net.GetRequestApi
import com.wei.wanandroidkotlin.net.response.Translation1
import com.wei.wanandroidkotlin.net.response.Translation2
import com.wei.wanandroidkotlin.net.retrofit.RetrofitHelper
import com.wei.wanandroidkotlin.rx.RxBus
import com.wei.wanandroidkotlin.rx.RxOperators
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttons: java.util.ArrayList<ButtonBean>
    private val num: Int
        get() = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, num.toString())
        initButtons()
        initView()
        textRxBus()
        window.decorView.postDelayed({ test() }, 100L)
    }

    private fun initButtons() {
        buttons = ArrayList()
        buttons.add(ButtonBean(1, "SoftInputMode软键盘适配"))
        buttons.add(ButtonBean(1, "Context的几种应用及区别"))
        buttons.add(ButtonBean(1, "Android版本及对应的Api"))
    }

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

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        adapter.setOnClickListener(object : QuickAdapter.OnClickListener<ButtonBean> {
            override fun onClick(v: View, t: ButtonBean) {
                Log.e(TAG, t.text)
            }
        })
//        recyclerView.addItemDecoration()
        recyclerView.itemAnimator = DefaultItemAnimator()
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

}

