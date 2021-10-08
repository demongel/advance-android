package com.shakespace.advanceandroid.chapter08rxjava

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 辅助操作符
 */

fun main() {
    doRx5()

    try {
        Thread.sleep(10000)
    } catch (e: Exception) {
        println(e.message)
    }
}

@SuppressLint("CheckResult")
fun doRx5() {

    val obs = Observable.create<Int> {
        it.onNext(1)
        it.onComplete()
    }
    obs.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.computation())
        .subscribe {
            println("observeOn : ${Thread.currentThread().name}")
        }


    /**
     * 如果原始 Observable 过了指定的一段时长没有发射任何数据，timeout 操作符会以一个onError通知终止这个Observable，
     * 或者继续执行一个备用的Observable。
     *
     * result:
     * timeout 0
     * timeout 1
     * timeout 2
     * timeout 10
     * timeout 11
     */
    val obs2: Observable<Int> = Observable.create<Int> {
        for (i in 0..3) {
            try {
                Thread.sleep((i * 100).toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            it.onNext(i)
        }
        it.onComplete()
    }
        // 在超时时会切换到指定的备用的Observable，而不是发送错误通知
        .timeout(200, TimeUnit.MILLISECONDS, Observable.just(10, 11))
    obs2.subscribe {
        println("timeout $it")
    }

    /**
     * 延迟发射数据
     */
    Observable.create<Long> {
        println("delay : ${System.currentTimeMillis()}")
        it.onNext(System.currentTimeMillis())
    }.delay(2000, TimeUnit.MILLISECONDS)
        .doOnSubscribe { println("-- doOnSubscribe") }
        .doOnNext { println("-- doOnNext") }
        .doOnComplete { println("-- doOnComplete") }
        .doOnError { println("-- doOnError") }
        .doOnTerminate { println("-- doOnTerminate") }
        .doFinally { println("-- doFinally") }
        // doOnEach 当Observable每发射一项数据时就会调用它一次，包括 onNext、onError和 onCompleted
        .doOnEach { println("-- doOnEach") }
        .subscribe {
            println("delay :${System.currentTimeMillis()}  $it")
        }
}


