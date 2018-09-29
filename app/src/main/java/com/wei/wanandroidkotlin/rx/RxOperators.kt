package com.wei.wanandroidkotlin.rx

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
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

    fun testCombine() {
        // 二者区别：组合被观察者的数量，即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
//        Observable.concatArray(Observable.just(1, 2, 3),
//                Observable.create { emitter ->
//                    emitter.onNext(4)
//                    emitter.onNext(5)
//                    emitter.onNext(45)
//                    emitter.onError(NullPointerException())
//                    emitter.onComplete()
//                },
//                Observable.just(6, 7, 8),
//                Observable.just(6, 7, 8),
//                Observable.just(9, 10))

        // 二者区别：组合被观察者的数量，即merge（）组合被观察者数量≤4个，而mergeArray（）则可＞4个
//        区别上述concat（）操作符：同样是组合多个被观察者一起发送数据，但concat（）操作符合并后是按发送顺序串行执行
//        Observable.mergeArray(Observable.intervalRange(0, 10, 3, 2, TimeUnit.SECONDS),
//                Observable.intervalRange(11, 11, 2, 2, TimeUnit.SECONDS))

        // 若希望onError事件推迟到其它被观察者发送事件结束后才触发，则需要使用对应的concatDelayError() 或 mergeDelayError() 操作符
//        Observable.mergeArrayDelayError(Observable.just(1, 2, 3),
//                Observable.create { emitter ->
//                    emitter.onNext(4)
//                    emitter.onNext(5)
//                    emitter.onNext(45)
//                    emitter.onError(NullPointerException())
//                    emitter.onComplete()
//                },
//                Observable.just(6, 7, 8),
//                Observable.just(6, 7, 8),
//                Observable.just(9, 10))

//        val observable1 = Observable.create(ObservableOnSubscribe<Int> { emmit ->
//            emmit.onNext(1)
//            emmit.onNext(2)
//            emmit.onNext(3)
//            emmit.onComplete()
//        })
//
//        val observable2 = Observable.create(ObservableOnSubscribe<String> { emitter ->
//            emitter.onNext("A")
//            emitter.onNext("B")
//            emitter.onNext("C")
//            Log.e(TAG, "准备发送D及F")
//            emitter.onNext("D")
//            emitter.onNext("F")
//            emitter.onComplete()
//        })

        // 最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
//        Observable.zip(observable1, observable2, BiFunction<Int, String, String> { t1: Int, t2: String ->
//            t1.toString().plus(t2)
//        })

        // 当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据
//        Observable.combineLatest(
////                Observable.just(1, 11, 22),
//                Observable.intervalRange(1, 4, 2, 1, TimeUnit.SECONDS),
//                Observable.intervalRange(10, 3, 1, 2, TimeUnit.SECONDS),
//                BiFunction<Long, Long, Long> { t1, t2 ->
//                    Log.e(TAG, "合并的数据是：$t1 $t2")
//                    t1 + t2
//                })

        // 把被观察者需要发送的事件聚合成1个事件 & 发送；可用于计算一堆数据的累加结果或者累乘结果等
//        Observable.just(74, 45, 83, 90)
//                .reduce { data1, data2 ->
//                    Log.e(TAG, "本次计算的数据是： $data1 + $data2")
//                    data1 + data2
//                }.subscribe { result ->
//                    Log.e(TAG, "最终计算的结果是： $result")
//                }

        // 将被观察者Observable发送的数据事件收集到一个数据结构里
//        val list = java.util.ArrayList<Int>()
//        Observable.just(74, 45, 83, 90)
//                .collect(Callable { list }, BiConsumer<ArrayList<Int>, Int> { t1: ArrayList<Int>?, t2: Int? ->
//                    t1?.add(t2 ?: 0)
//                }).subscribe { result ->
//                    Log.e(TAG, "本次发送的数据是： $result")
//                }

        // 统计被观察者发送事件的数量
//        Observable.just(74, 45, 83, 90)
//                .count()
//                .subscribe { count ->
//                    Log.e(TAG, "发送的事件数量 =  $count")
//                }

        Observable.just(4, 5, 6)
                .startWithArray(1, 2, 3)
                .startWith(0)
                .subscribe(CusObserver())

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

    fun testBuffer() {
        Observable.just("1bc", "lajdl", "0212", "ab", "ieyirye", "dhdjhj", "ourouod")
                .buffer(3, 3)
                .subscribe(object : Observer<List<String>> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: List<String>) {
                        Log.d(TAG, " 缓存区里的事件数量 = ${t.size}")
                        for (item in t) {
                            Log.d(TAG, " 事件 = $item")
                        }
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    val strs = arrayListOf("1bc", "lajdl", "0212", "ab", "ieyirye", "dhdjhj", "ourouod")
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
//        Observable.never<String>()

        // 直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件;每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的
//        val stringObservable = Observable.defer { Observable.just(strs[0]) }
//        strs[0] = "ljadljlfjdljj"
//        stringObservable

        // 本质 = 延迟指定时间后，调用一次 onNext(0)； 该例子 = 延迟2s后，发送一个long类型数值，默认是0L
//        Observable.timer(2, TimeUnit.SECONDS)

        // 发送的事件序列 = 从0开始、每隔指定时间 就发送 无限递增1的的整数序列
//        Observable.interval(3, 2, TimeUnit.SECONDS)

        // 延迟2秒每隔2秒发送一次事件，从3-12依次发送；作用类似于interval（），但可指定发送的数据的数量
//        Observable.intervalRange(3, 10, 2, 2, TimeUnit.SECONDS)

        // 作用类似于intervalRange（），但区别在于：无延迟发送事件，也不能指定时间间隔
//        Observable.range(3, 10)

        // 类似于range（），区别在于该方法支持数据类型 = Long
        Observable.rangeLong(3, 10)
                .subscribe(CusObserver())
    }

}