package com.wei.wanandroidkotlin.rx

import io.reactivex.subjects.PublishSubject

/**
 * @author XiangWei
 * @since 2018/9/14
 */
class RxBus {
    private var eventIds = HashSet<Int>()
    private val subject = PublishSubject.create<Event>().toSerialized()

    companion object {
        private var rxBus: RxBus? = null
            get() {
                if (field == null) {
                    field = RxBus()
                }
                return field
            }

        fun get(): RxBus {
            return rxBus ?: RxBus()
        }

        fun close() {
            rxBus?.release()
            rxBus = null
        }
    }

    /**
     *  发送事件
     */
    fun post(event: Event) {
        subject.onNext(event)
    }

    fun post(id: Int, any: Any? = null) {
        subject.onNext(object : Event {
            override val id: Int
                get() = id
            override val any: Any?
                get() = any
        })
    }

    /**
     *  接收的事件id
     */
    fun accept(vararg id: Int) {
        id.forEach { eventIds.add(it) }
    }

    fun subscribe(block: (event: Event) -> Unit) {
        subject.filter {
            eventIds.contains(it.id)
        }.subscribe {
            block(it)
        }
    }

    private fun release() {
        subject.onComplete()
    }

    interface Event {
        val id: Int
        val any: Any?
    }
}