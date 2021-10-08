package com.shakespace.advanceandroid.chapter08rxjava.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class RxBus private constructor() {

    private val subject: Subject<Any> = PublishSubject.create<Any>().toSerialized()

    fun post(ob: Any) {
        subject.onNext(ob)
    }

//    fun listenTo(clazz: Class<RxEvent?>?): Observable<RxEvent?>? {
//        return this.subject.ofType(clazz)
//    }

    fun <T> toObservable(eventType: Class<T>?): Observable<T> {
        return subject.ofType(eventType)
    }

    companion object {
        @Volatile
        private var rxBus: RxBus? = null

        fun getInstance(): RxBus {
            if (rxBus == null) {
                synchronized(RxBus::class.java) {
                    if (rxBus == null) {
                        rxBus = RxBus()
                    }
                }
            }
            return rxBus!!
        }
    }

}