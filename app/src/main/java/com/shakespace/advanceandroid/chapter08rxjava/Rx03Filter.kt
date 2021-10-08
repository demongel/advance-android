package com.shakespace.advanceandroid.chapter08rxjava

import android.annotation.SuppressLint
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 过滤操作符
 */

fun main() {
    doRx3()

    try {
        Thread.sleep(10000)
    } catch (e: Exception) {
        println(e.message)
    }
}

@SuppressLint("CheckResult")
fun doRx3() {

    /**
     * 过滤出满足条件的元素
     */
    Observable.just(1, 2, 3, 4).filter {
        it > 2
    }.subscribe {
        println("filter : $it")
    }

    /**
     * elementAt操作符用来返回指定位置的数据。和它类似的有elementAtOrDefault(int，T)，其可以允许 默认值
     */
    Observable.just(1, 2, 3, 4).elementAt(2)
        .subscribe {
            println("elementAt : $it")
        }

    /**
     * distinct 用来去重，其只允许还没有发射过的数据项通过
     */
    Observable.just(1, 2, 2, 3, 4, 1).distinct()
        .subscribe {
            println("distinct : $it")
        }

    /**
     * skip操作符将源Observable发射的数据过滤掉前n项;而take操作符则只取前n项;另外还有skipLast和 takeLast操作符，则是从后面进行过滤操作。
     */
    Observable.just(1, 2, 3, 4, 5, 6).skip(2)
        .subscribe {
            println("skip : $it")
        }

    Observable.just(1, 2, 3, 4, 5, 6).take(2)
        .subscribe {
            println("take : $it")
        }

    /**
     * 返回的是 Completable , ignoreElements操作符忽略所有源Observable产生的结果，只把Observable的onCompleted和onError事件 通知给订阅者
     */
    Observable.just(1, 2, 3, 4, 5, 6).ignoreElements()
        .subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {
                println("ignoreElements : onSubscribe")
            }

            override fun onComplete() {
                println("ignoreElements : onComplete")
            }

            override fun onError(e: Throwable) {
                println("ignoreElements : onError")
            }

        }
        )

    /**
     * throttleFirst操作符则会定期发射这个时间段里源Observable发射的第一个数据，
     * throttleFirst操作符默认 在computation 调度器上执行
     */
    Observable.create(ObservableOnSubscribe<Int> { emitter ->
        for (i in 0..9) {
            emitter.onNext(i)
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        emitter.onComplete()
    }).throttleFirst(150, TimeUnit.MILLISECONDS)
        .subscribe {
            println("throttleFirst : $it")
            // 结果： 0/2/4/6/8
        }


    /**
     * 源Observable每次发射出来一个数据后就会进行计时。如果在设定好的时间结束前源 Observable有新的数据发射出来，这个数据就会被丢弃，
     * 同时throttleWithTimeOut重新开始计时。如果每次 都是在计时结束前发射数据，那么这个限流就会走向极端:只会发射最后一个数据。其默认在 computation 调度器上执行。
     *
     * e.g : 元素0%3=0 ，下一个元素1将在300ms后发出，超出了150ms,因而0会被处理
     * 1 因为100ms 还有2，所以1不会被处理，同样2也不会，3会
     */
    Observable.create(ObservableOnSubscribe<Int> { emitter ->
        for (i in 0..9) {
            emitter.onNext(i)
            var sleep = 100
            if (i % 3 == 0) {
                sleep = 300
            }
            try {
                Thread.sleep(sleep.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        emitter.onComplete()
    }).throttleWithTimeout(150, TimeUnit.MILLISECONDS)
        .subscribe {
            println("throttleWithTimeout : $it")
            // 结果： 0/2/4/6/8
        }
}
