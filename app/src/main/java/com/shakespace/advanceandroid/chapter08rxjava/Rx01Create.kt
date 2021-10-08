package com.shakespace.advanceandroid.chapter08rxjava

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * 创建操作符
 */

fun main() {
    doRx()

    try {
        Thread.sleep(10000)
    } catch (e: Exception) {
        println(e.message)
    }
}

@SuppressLint("CheckResult")
fun doRx() {
    Observable.create(ObservableOnSubscribe<String> {
        it.onNext("a")
        it.onComplete()
    }).subscribe {
        println("create $it")
    }

    Observable.just("a", "b") // 调用 fromArray
        .subscribe {
            println("just : $it")
        }

    /**
     * 间隔发射数据
     */
    Observable.interval(3, TimeUnit.SECONDS).subscribeOn(Schedulers.single())
        .subscribe {
            println("interval : ${System.currentTimeMillis()} + $it")
        }

    /**
     * 发射连续数据，近似于for循环
     * 第一个参数不能小于0， 第二个参数是表示数据个数
     */
    Observable.range(0, 5)
        .subscribe {
            println("range : ${System.currentTimeMillis()} + $it")
        }

    /**
     * repeat : 重复N次
     */
    Observable.range(0, 3)
        .repeat(2)
        .subscribe {
            println("range + repeat : + $it")
        }

}
