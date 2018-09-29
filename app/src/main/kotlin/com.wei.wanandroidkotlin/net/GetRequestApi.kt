package com.wei.wanandroidkotlin.net

import com.wei.wanandroidkotlin.net.response.Translation1
import com.wei.wanandroidkotlin.net.response.Translation2

import io.reactivex.Observable
import retrofit2.http.GET


/**
 * @author XiangWei
 * @since 2018/9/29
 */
interface GetRequestApi {

    @get:GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    val call: Observable<Translation1>

    @get:GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    val call_2: Observable<Translation2>
}
