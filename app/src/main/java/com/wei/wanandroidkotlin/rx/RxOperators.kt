package com.wei.wanandroidkotlin.rx

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class RxOperators {
    val TAG = javaClass.name
    lateinit var disposable: Disposable

    companion object {
        val testArray = arrayListOf(1, 29, 20, 32, 11, 19)
    }

    inner class CusObserver<T> : Observer<T> {
        override fun onSubscribe(d: Disposable) {
            disposable = d
            Log.e(TAG, "准备接收事件......")
        }

        override fun onNext(t: T) {
            Log.e(TAG, "接收到事件：$t")
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "接收事件出错：${e.cause}")
        }

        override fun onComplete() {
            Log.e(TAG, "事件处理完成！")
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
        }.subscribe(CusObserver())
    }

    class Box<T>(t: T) {
        val value = t
    }

    val strs = arrayListOf("1bc", "lajdl", "0212")
    fun testCreate() {
//        Observable.create(ObservableOnSubscribe<String> { emitter ->
//            emitter.onNext("12121")
//            emitter.onNext("abc")
////            disposable.dispose()
//            emitter.onNext("71937jkjd")
//            emitter.onComplete()
//        })

        // just(..)里面其实是调用的fromArray()。subscribe()的时候会触发一个run方法,遍历所有待发射事件,如果事件不被人为取消,则最终自动回调onComplete
//        Observable.just("1bc", "lajdl", "0212")
//        Observable.fromArray("1bc", "lajdl", "0212")

        // 内部执行原理与上面相似
//        Observable.fromIterable(strs)

        // 仅发送Error事件，直接通知异常
//        Observable.error<RuntimeException>(RuntimeException())

        // 仅发送Complete事件，直接通知完成
//        Observable.empty<String>()

        // 不发送任何事件
        Observable.never<String>()
                .subscribe(CusObserver())
    }

}