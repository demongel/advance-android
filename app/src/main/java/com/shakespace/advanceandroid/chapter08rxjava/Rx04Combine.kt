package com.shakespace.advanceandroid.chapter08rxjava

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * 组合操作符
 */

fun main() {
    doRx4()

    try {
        Thread.sleep(10000)
    } catch (e: Exception) {
        println(e.message)
    }
}

@SuppressLint("CheckResult")
fun doRx4() {

    /**
     *startWith操作符会在源Observable发射的数据前面插上一些数据
     */
    Observable.just(3, 4, 5).startWith(listOf(1, 2))
        .subscribe {
            println("startWith : $it")
        }

    /**
     * merge操作符将多个Observable合并到一个Observable中进行发射，merge可能会让合并的Observable发射 的数据交错。
     */
    val obs1 = Observable.just(1, 2, 3).subscribeOn(Schedulers.io())
    val obs2 = Observable.just(4, 5, 6)
    Observable.merge(obs1, obs2).subscribe {
        println("merge : $it")
    }

    /**
     * 将多个 Observable 发射的数据进行合并发射。concat 严格按照顺序发射数据，
     * 前一个Observable没发射 完成是不会发射后一个Observable的数据的
     */
    Observable.concat(obs1, obs2).subscribe {
        println("concat : $it")
    }

    /**
     * zip操作符合并两个或者多个Observable发射出的数据项，根据指定的函数变换它们，并发射一个新值。
     * 只有当原始的Observable中的每一个都发射了一条数据时 zip 才发射数据
     */
    val obs3 = Observable.just("a", "b", "c", "d")
    Observable.zip(obs1, obs3, { t1, t2 -> t1.toString() + t2 })
        .subscribe {
            println("zip : $it")
        }

    /**
     * 有可能是 3a/3b/3c/3d 也有可能是 1d/2d/3d
     * 甚至是
     * combineLatest : 2a
     * combineLatest : 3a
     * combineLatest : 3b
     * combineLatest : 3c
     * combineLatest : 3d
     * 只有当原始的Observable中的每一个都发射了一条数据时 zip 才发射数据
     * combineLatest是任意一个发射了数据，就会和另一个最新的数据组合
     */
    val obs4 = Observable.just(1, 2, 3).subscribeOn(Schedulers.io())
    val obs5 = Observable.just("a", "b", "c", "d").subscribeOn(Schedulers.io())
    Observable.combineLatest(obs4, obs5, { t1, t2 -> t1.toString() + t2 })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe {
            println("combineLatest : $it")
        }



}
