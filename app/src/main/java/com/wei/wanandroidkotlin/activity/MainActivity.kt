package com.wei.wanandroidkotlin.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSeekBar
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.wei.wanandroidkotlin.R
import com.wei.wanandroidkotlin.UriUtil
import com.wei.wanandroidkotlin.net.GetRequestApi
import com.wei.wanandroidkotlin.net.response.Translation1
import com.wei.wanandroidkotlin.net.response.Translation2
import com.wei.wanandroidkotlin.net.retrofit.RetrofitHelper
import com.wei.wanandroidkotlin.rx.RxBus
import com.wei.wanandroidkotlin.rx.RxOperators
import com.wei.wanandroidkotlin.widgets.SideBar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity(), SideBar.OnChooseLetterChangedListener, com.wei.wanandroidkotlin.widget.SideBar.OnTouchLetterListener {

    private val TAG = "MainActivity"
    private var contentTv: TextView? = null
    private var seekBar: AppCompatSeekBar? = null
    private var etPhone: TextView? = null
    private var etPass: TextView? = null
    private var btnLogin: Button? = null
    private val num: Int
        get() = 10
    private lateinit var sideBar: SideBar
    private lateinit var kotlinSideBar: com.wei.wanandroidkotlin.widget.SideBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, num.toString())
        initView()
        textRxBus()
        window.decorView.postDelayed({ test() }, 100L)
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
        contentTv = findViewById(R.id.tv_content)
        contentTv?.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            //设置类型，我这里是任意类型，任意后缀的可以这样写。
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, 1)
        }
//        seekBar = findViewById(R.id.seekBar)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            seekBar?.thumb?.setColorFilter(Color.parseColor("#ec6a88"), PorterDuff.Mode.SRC_ATOP)
//        }
//        startActivity(Intent(this, UpdateActivity :: class.java))
        sideBar = findViewById(R.id.sideBar)
        sideBar.setOnTouchingLetterChangedListener(this)

        kotlinSideBar = findViewById(R.id.kotlin_sideBar)
//        kotlinSideBar.setSingleHeight(UIUtil.dpToPx(11))
        kotlinSideBar.setOnTouchLetterChangeListener(this)
    }

    override fun onChooseLetter(s: String?) {
        Log.e(TAG, s)
    }

    override fun onNoChooseLetter() {

    }

    override fun onTouchLetterChanged(index: Int, s: String, isFocus: Boolean) {
        val str = index.toString() + ", " + s + ", " + isFocus
        Log.e(TAG, str)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val uri = data?.data
                val size = contentResolver.openInputStream(uri as Uri)?.available()
                if (size != null) {
                    if (size > (30 * 1000 * 1000)) {
                        Toast.makeText(this, "无法发送大于30MB的文件", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val path = UriUtil.getPath(this, uri)
                    Log.e(TAG, "uri : $uri" + ", path : $path" + ", 文件名 : " + path?.substring(path.lastIndexOf("/") + 1) + ", size = $size")
                    Log.e(TAG, "realpath : " + UriUtil.getPath(this, uri))
                }
            }
        }
    }

}

