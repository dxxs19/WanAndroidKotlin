package com.wei.wanandroidkotlin.rx

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class RxOperators {
    private val TAG = javaClass.name
    companion object {
        val testArray = arrayListOf(1, 29, 20, 32, 11, 19)
    }
    private val observer = object : Observer<String> {

        override fun onSubscribe(d: Disposable) {

        }

        override fun onNext(s: String) {
            Log.e(TAG, "结果：$s")
        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {

        }
    }

    fun testFlatMap() {
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
        }).flatMap { int ->
            val strs = ArrayList<String>()
            for (i in 0..3) {
                strs.add(int.toString() + "")
            }
            Observable.fromIterable(strs).delay(10, TimeUnit.MILLISECONDS)
        }.subscribe(observer)
    }


}