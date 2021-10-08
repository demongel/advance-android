package com.shakespace.advanceandroid.chapter08rxjava

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

/**
 * 条件操作符
 */

fun main() {
    doRx7()

    try {
        Thread.sleep(10000)
    } catch (e: Exception) {
        println(e.message)
    }
}

@SuppressLint("CheckResult")
fun doRx7() {

    /**
     * all操作符根据一个函数对源Observable发射的所有数据进行判断，最终返回的结果就是这个判断结果。
     */
    Observable.just(1, 2, 3)
        .all {
            it < 2
        }.subscribe(Consumer {
            println("all : $it")
        })

    /**
     * contains 操作符用来判断源 Observable 所发射的数据是否包含某一个数据。如果包含该数据，会返回 true;
     * 如果源Observable已经结束了却还没有发射这个数据，则返回false。
     * isEmpty操作符用来判断源 Observable 是否发射过数据。
     * 如果发射过该数据，就会返回 false;如果源Observable已经结束了却还没有发 射这个数据，则返回true。
     */
    Observable.just(1, 2, 3).isEmpty
        .subscribe(Consumer {
            println("isEmpty : $it")
        })

    Observable.just(1, 2, 3).contains(1)
        .subscribe(Consumer {
            println("contains : $it")
        })

    /**
     * amb 操作符对于给定两个或多个 Observable，它只发射首先发射数据或通知的那个Observable的所有数 据
     */
    Observable.amb(
        listOf(
            Observable.just(1, 2, 3).delay(2, TimeUnit.SECONDS),
            Observable.just(4, 5, 6)
        )
    ).subscribe {
        //第一个Observable延时2s发射，所以很显然最终只会发射第二个Observable
        println("amb : $it")
    }

    /**
     * 如果原始Observable没有发射数据，就发射一个默认数据
     */
    Observable.empty<Int>().defaultIfEmpty(3).subscribe {
        println("defaultIfEmpty : $it")
    }

}


