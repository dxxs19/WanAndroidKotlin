package com.wei.wanandroidkotlin

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSeekBar
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var contentTv: TextView? = null
    private var seekBar: AppCompatSeekBar? = null
    private val num: Int
        get() = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, num.toString())
        initView()
        textRx()
    }

    private fun textRx() {
        Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("I come from observable subscribe!")
        }).map {
            kotlin.run {
                return@map it.length
            }
        }
//                .map { t: Int -> t.let {
//                    t.toString().length
//                    Log.e(TAG, "2nd map : " + t)
//                } }
                .subscribe {
                    Log.e(TAG, "subscribe : " + it)
                }
    }

    private fun initView() {
        contentTv = findViewById(R.id.tv_content)
        contentTv?.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"//设置类型，我这里是任意类型，任意后缀的可以这样写。
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, 1)
        }
//        seekBar = findViewById(R.id.seekBar)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            seekBar?.thumb?.setColorFilter(Color.parseColor("#ec6a88"), PorterDuff.Mode.SRC_ATOP)
//        }
//        startActivity(Intent(this, UpdateActivity :: class.java))
    }

    fun double(num: Int): Int {
        return num * 2
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
                    // api < 19 : /storage/emulated/0/server.log.5
                    val path = UriUtil.getPath(this, uri)
                    Log.e(TAG, "uri : " + uri + ", path : " + path + ", 文件名 : " + path?.substring(path.lastIndexOf("/") + 1) + ", size = " + conversionUnit(size))
                    Log.e(TAG, "realpath : " + UriUtil.getPath(this, uri))
//                    Log.e(TAG, "realpath : " + getRealFilePath(uri) )
                }
            }
        }
    }

    private fun getRealFilePath(uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        var data: String? = null
        var size: Int
        if (scheme == null)
            data = uri.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

    private fun conversionUnit(size: Int): String {
        var result = StringBuilder()
        if (size < 1000) {
            result = result.append(size).append(" B")
        } else if (size < 1000 * 1000) {
            result = result.append(size / 1000)
                    .append(" KB")
        } else {
            result = result.append(size / (1000 * 1000))
                    .append(" MB")
        }
        return result.toString()
    }
}

