package com.shakespace.advanceandroid.chapter04thread

import android.telecom.Call
import java.lang.Exception
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.concurrent.thread

/**
 * created by  shakespace
 * 2021/4/14  14:32
 */

/***
 * 1. 使用 Thread
 * 2. 使用Runnable
 * 3. 使用线程池
 */
fun main() {
    object : Thread("New Thread") {
        override fun run() {
            println("${Thread.currentThread()} run in New Thread")
        }
    }.start()

    Runnable { println("${Thread.currentThread()} run in Runnable") }.run()

    // 默认参数 自己会start
    thread { println("${Thread.currentThread()} run in Kotlin 自带 thread") }

    object : Thread(Runnable
    { println("${Thread.currentThread()} run in Thread with Runnable") }) {}.start()

    val executors = Executors.newSingleThreadExecutor()
    val future = executors.submit(Callable<String> { "${Thread.currentThread()} in Callable" })
    try {
        println(future.get())
        //使用完不关闭，程序不会结束
        executors.shutdown()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}