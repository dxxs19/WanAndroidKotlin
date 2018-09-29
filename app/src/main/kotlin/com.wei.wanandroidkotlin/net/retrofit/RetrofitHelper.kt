package com.wei.wanandroidkotlin.net.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author XiangWei
 * @since 2018/9/29
 */
object RetrofitHelper {

    fun getRetrofit(baseUrl:String): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}