package com.shakespace.advanceandroid.chapter08rxjava

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 错误处理操作符
 */

fun main() {
    doRx6()

    try {
        Thread.sleep(10000)
    } catch (e: Exception) {
        println(e.message)
    }
}

@SuppressLint("CheckResult")
fun doRx6() {

    /**
     * retry操作符不会将原始Observable的onError通知传递给观察者，它会订阅这个Observable，再给它一次机会无错误地完成其数据序列。
     * retry总是传递onNext通知给观察者，由于重新订阅，这可能会造成数据项重复。
     */
    Observable.create<Int> {
        for (i in 0..4) {
            if (i == 1) {
                it.onError(Throwable("Throwable"))
            } else {
                it.onNext(i)
            }
        }
        it.onComplete()
    }.retry(2).subscribe(object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            println("retry : onSubscribe")
        }

        override fun onNext(t: Int) {
            println("retry : $t")
        }

        override fun onError(e: Throwable) {
            println("retry : onError ${e.message}")
        }

        override fun onComplete() {
            println("retry : onComplete")
        }

    })

    /**
     * catch操作符拦截原始Observable的onError通知，将它替换为其他数据项或数据序列，让产生的 Observable能够正常终止或者根本不终止。
     * RxJava将catch实现为以下 3个不同的操作符。
    • onErrorReturn:Observable遇到错误时返回原有Observable行为的备用Observable，备用Observable会忽略原有Observable的onError调用，不会将错误传递给观察者。作为替代，它会发射一个特殊的项并调用观察者的onCompleted方法。
    • onErrorResumeNext:Observable遇到错误时返回原有Observable行为的备用Observable，备用 Observable会忽略原有Observable的onError调用，不会将错误传递给观察者。作为替代，它会发射备用 Observable的数据。
    • onExceptionResumeNext:它和onErrorResumeNext类似。不同的是，如果onError收到的Throwable不是 一个Exception，它会将错误传递给观察者的onError方法，不会使用备用的Observable。
     */
    Observable.create<Int> {
        for (i in 0..4) {
            if (i > 2) {
                it.onError(Throwable("Throwable"))
            }
            it.onNext(i)
        }
        it.onComplete()
    }.onErrorReturn {
        println(it.message)
        6
    }.subscribe(object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            println("onErrorReturn : onSubscribe")
        }

        override fun onNext(t: Int) {
            println("onErrorReturn : $t")
        }

        override fun onError(e: Throwable) {
            println("onErrorReturn : onError ${e.message}")
        }

        override fun onComplete() {
            println("onErrorReturn : onComplete")
        }

    })
}


