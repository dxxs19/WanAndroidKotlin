package com.wei.wanandroidkotlin.activity.softinput

import android.text.Editable
import android.widget.EditText
import com.wei.wanandroidkotlin.R
import com.wei.wanandroidkotlin.activity.BaseActivity

class SoftInputModeActivity : BaseActivity() {

    private lateinit var etMode: EditText

    override fun layoutResId(): Int {
        return R.layout.activity_soft_input_mode
    }

    override fun initData() {
    }

    override fun initNavBar() {
    }

    override fun initView() {
        etMode = findViewById(R.id.et_mode)
//        val content = String.format(resources.getString(R.string.current_mode), "stateVisible|adjustResize")
//        etMode.setText(content)
    }

}
