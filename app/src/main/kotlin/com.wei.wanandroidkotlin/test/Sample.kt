package com.wei.wanandroidkotlin.test

import com.wei.wanandroidkotlin.util.log

/**
 * @author XiangWei
 * @since 2018/10/19
 */
object Sample {
    fun foo() {
        log("member foo()")
    }
}

fun Sample.foo() {
    log("extension foo()")
}