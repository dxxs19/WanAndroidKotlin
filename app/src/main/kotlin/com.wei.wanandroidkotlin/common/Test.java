package com.wei.wanandroidkotlin.common;


import android.content.Context;
import android.widget.EditText;

/**
 * @author XiangWei
 * @since 2018/10/8
 */
public class Test {

    private void test(Context context) {
        String content =  "stateVisible|adjustResize";
        EditText etMode = new EditText(context);
        etMode.setText(content);
    }

}