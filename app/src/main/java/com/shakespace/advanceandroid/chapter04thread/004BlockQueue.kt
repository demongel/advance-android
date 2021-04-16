package com.shakespace.advanceandroid.chapter04thread

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.atomic.AtomicInteger

/**
 * created by  shakespace
 * 2021/4/16  13:26
 */

val size = 10
val blockingQueue = ArrayBlockingQueue<Int>(size)
var atomicCount = AtomicInteger(0)
fun main() {
    Consumer2().start()
    Producer2().start()
}

class Consumer2 : Thread() {
    override fun run() {
        while (true) {
            try {
                blockingQueue.take()
                println("-------移除了数据 + ${atomicCount.decrementAndGet()}")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}

class Producer2 : Thread() {
    override fun run() {
        while (true) {
            try {
                blockingQueue.put(1)
                // blockQueue 是支持多线程的，但是 下面打印不是，所以打印值会不断跳动
                println("-------添加了数据 + ${atomicCount.getAndIncrement()}")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}