package com.wei.wanandroidkotlin.rx

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

class RxOperators {
    val TAG = javaClass.name
    lateinit var disposable: Disposable

    companion object {
        val testArray = arrayListOf(1, 29, 20, 32, 11, 19)
    }

    inner class CusConsumer<T> : Consumer<T> {
        override fun accept(t: T) {
            Log.e(TAG, "事件结果: $t")
        }
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

    fun testBoolean() {
//        Observable.just(1, 2, 3, 4)
        // 判断发送的每项数据是否都满足 设置的函数条件
//                .all { t -> t < 4 }

        // 若发送的数据满足该条件，则发送该项数据；否则不发送
//                .takeWhile { t ->
//                    t < 4
//                }

        // 直到该判断条件 = false时，才开始发送Observable的数据
//                .skipWhile {
//                    it < 2
//                }

        // 执行到某个条件时，停止发送事件
//                .takeUntil {
//                    it > 2
//                }

//        Observable.interval(1, TimeUnit.SECONDS)
//                // 等到 skipUntil（） 传入的Observable开始发送数据，（原始）第1个Observable的数据才开始发送数据
//                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
////                .subscribe(Consumer {
////                    Log.e(TAG, "result is $it")
////                })

        // 判定两个Observables需要发送的数据是否相同
//        Observable.sequenceEqual(
//                Observable.just(1, 2, 3),
//                Observable.just(1, 2, 4)
//        )

//        Observable.just(1, 2, 3, 4)
//                // 判断发送的数据中是否包含指定数据
////                .contains(3)
//
//                // 判断发送的数据是否为空
//                .isEmpty

        val list = java.util.ArrayList<ObservableSource<Int>>()
        list.add(Observable.just(1, 2, 3).delay(2, TimeUnit.SECONDS))
        list.add(Observable.just(4, 5, 6).delay(2, TimeUnit.SECONDS))
        // 当需要发送多个 Observable时，只发送 先发送数据的Observable的数据，而其余 Observable则被丢弃。
        Observable.amb(list)
                .subscribe(CusConsumer())
    }

    fun testFilter() {
//        Observable.just(1, 29, 20, 32, 11, 19)
//                // 过滤 特定条件的事件
//                .filter { t: Int ->
//                    t > 20
//                }

//        Observable.just(1, "Carson", 3, "Ho", 5)
        // 过滤 特定数据类型的数据
//                .ofType(String::class.java)

        // 跳过正序的前1项
//                .skip(1)

        // 跳过正序的后2项
//                .skipLast(2)

//        Observable.intervalRange(1, 10, 0, 1, TimeUnit.SECONDS)
//                .skip(1, TimeUnit.SECONDS) // 跳过第1s发送的数据
//                .skipLast(2, TimeUnit.SECONDS) // 跳过最后2s发送的数据

//        Observable.just("a", "dd", "dd", "p", "a")
        // 过滤事件序列中重复的事件
//                .distinct()

        // 过滤事件序列中 连续重复的事件
//                .distinctUntilChanged()

        // 指定观察者最多能接收到的事件数量
//                .take(3)

        // 指定观察者只能接收到被观察者发送的最后几个事件
//                .takeLast(2)

//        Observable.create(ObservableOnSubscribe<Int> { e ->
//            e.onNext(1)
//            Thread.sleep(500)
//
//            e.onNext(2)
//            Thread.sleep(400)
//
//            e.onNext(3)
//            Thread.sleep(300)
//
//            e.onNext(4)
//            Thread.sleep(300)
//
//            e.onNext(5)
//            Thread.sleep(300)
//
//            e.onNext(6)
//            Thread.sleep(400)
//
//            e.onNext(7)
//            Thread.sleep(300)
//            e.onNext(8)
//
//            Thread.sleep(300)
//            e.onNext(9)
//
//            Thread.sleep(300)
//            e.onComplete()
////        }).throttleFirst(1, TimeUnit.SECONDS) //每1秒中采用数据
////        }).throttleLast(1, TimeUnit.SECONDS) //每1秒中采用数据
////        }).sample(1, TimeUnit.SECONDS) //在某段时间内，只发送该段时间内最新（最后）1次事件,与 throttleLast（） 操作符类似
////        }).throttleWithTimeout(1, TimeUnit.SECONDS) //发送数据事件时，若2次发送事件的间隔＜指定时间，就会丢弃前一次的数据，直到指定时间内都没有新数据发射时才会发送后一次的数据
//        }).debounce(1, TimeUnit.SECONDS) //发送数据事件时，若2次发送事件的间隔＜指定时间，就会丢弃前一次的数据，直到指定时间内都没有新数据发射时才会发送后一次的数据

        Observable.just("a", "dd", "dd", "p", "a")
                // 仅选取第1个元素
//                .firstElement()

                // 选取最后一个元素
//                .lastElement()

                // 指定接收某个元素（通过 索引值 确定）
//                .elementAt(3)

                // 获取的位置索引 ＞ 发送事件序列长度时，设置默认参数
//                .elementAt(5, "fh")
                .elementAtOrError(5)
                .subscribe(Consumer<String> {
                    Log.e(TAG, "获取到的事件元素是： $it")
                }, Consumer<Throwable> {
                    Log.e(TAG, "事件异常： $it")
                })
//                .subscribe(CusObserver())
    }

    fun testCombine() {
        // 二者区别：组合被观察者的数量，即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
//        Observable.concatArray(Observable.just(1, 2, 3),
//                Observable.create { emitter ->
//                    emitter.onNext(4)
//                    emitter.onNext(5)
//                    emitter.onNext(45)
//                    emitter.onError(NullPointerExceprrrrrrrrrrrrrrtion())
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

//        Observable.just(4, 5, 6)
//                // 前面的事件（4，5，6）会延迟3秒发送
//                .delay(3, TimeUnit.SECONDS)
//                .startWithArray(1, 2, 3)
//                .startWith(0)
//                .subscribe(CusObserver())

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
        Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("12121")
            emitter.onNext("abc")
//            disposable.dispose()
            emitter.onError(NullPointerException())
            emitter.onNext("71937jkjd")
            emitter.onComplete()
        })
                // 1. 当Observable每发送1次数据事件就会调用1次
                .doOnEach {
                    Log.e(TAG, "doOnEach: " + it.value)
                }
                // 2. 执行Next事件前调用
                .doOnNext {
                    Log.e(TAG, "doOnNext: $it")
                }
                // 3. 执行Next事件后调用(Observer的onNext()之后调用)
                .doAfterNext {
                    Log.e(TAG, "doAfterNext: $it")
                }
                // 4. Observable正常发送事件完毕后调用
                .doOnComplete {
                    Log.e(TAG, "doOnComplete: ")
                }
                // 5. Observable发送错误事件时调用
//                .doOnError { throwable ->
//                    Log.e(TAG, "doOnError: ${throwable.message}")
//                }
//                .onErrorReturn { throwable ->
//                    Log.e(TAG, "在onErrorReturn处理了错误: ${throwable.toString()}")
//                    return@onErrorReturn "error_return"
//                }
                // 遇到错误时，发送1个新的Observable
//                .onErrorResumeNext { t: Throwable ->
//                    Log.e(TAG, "在onErrorResumeNext处理了错误: $t")
//                    // 发生错误事件后，发送一个新的被观察者 & 发送事件序列
//                    Observable.just("error_retry")
//                }
                // 遇到异常时，发送1个新的Observable
//                .onExceptionResumeNext(object : Observable<String>() {
//                    override fun subscribeActual(observer: Observer<in String>?) {
//                        observer?.onNext("ex")
//                        observer?.onNext("ception")
//                        observer?.onNext("resumeNext")
//                    }
//                })

                // 遇到错误时，让被观察者重新发射数据（若一直错误，则一直重新发送
//                .retry()

                // 返回false，让被观察者重新发射数据（若一直错误，则一直重新发送
//                .retryUntil{
//                    false
//                }

//                .repeat(3)

                // 6. 观察者订阅时调用（先于Observer的onSubscribe调用）
                .doOnSubscribe {
                    Log.e(TAG, "doOnSubscribe: ")
                }
                // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
                .doAfterTerminate {
                    Log.e(TAG, "doAfterTerminate")
                }
                // 8. 最后执行
                .doFinally {
                    Log.e(TAG, "doFinally")
                }
                // 若仅发送了Complete事件，默认发送 值 = "default"
                .defaultIfEmpty("default")

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

                // 直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的
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
//        Observable.rangeLong(3, 10)
                .subscribe(CusObserver())
    }

}