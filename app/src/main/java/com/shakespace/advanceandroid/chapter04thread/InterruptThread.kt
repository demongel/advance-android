package com.shakespace.advanceandroid.chapter04thread

import java.util.concurrent.TimeUnit

/**
 * created by  shakespace
 * 2021/4/14  16:31
 */

fun main() {
    val thread = Thread(MoonRunnable(), "MoonThread")
    thread.start()
    TimeUnit.MILLISECONDS.sleep(10)
    // 调用 interrupt 并不会马上结束 线程， 被中断的线程可以决定如何响应中断
    thread.interrupt()
}

class MoonRunnable : Runnable {
    var i = 0L
    override fun run() {
        // 也可以通过外部的 boolean 来管理
        while (!Thread.currentThread().isInterrupted) {
            i++
            println(i)
        }
        println("Thread Stop")
    }
}
