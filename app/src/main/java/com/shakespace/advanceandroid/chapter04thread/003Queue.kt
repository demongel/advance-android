package com.shakespace.advanceandroid.chapter04thread

import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/**
 * created by  shakespace
 * 2021/4/15  22:26
 */

fun main() {
    Consumer().start()
    Producer().start()
}

const val queueSize = 20
val queue = PriorityQueue<Int>(queueSize)
var count = 0
val lock = Object()

class Consumer : Thread() {
    override fun run() {
// 如果是方法 使用 @Synchronized
        while (true) {
            synchronized(lock) {
                while (queue.size == 0) {
                    try {
                        println("等待数据")
                        lock.wait()
                    } catch (e: InterruptedException) {
                        lock.notifyAll()
                    }
                }
                queue.poll()
                count--
                println("-------移除了数据 + $count")
                lock.notifyAll()
            }
        }
    }
}


class Producer : Thread() {
    override fun run() {
// 如果是方法 使用 @Synchronized
        while (true) {
            synchronized(lock) {
                while (queue.size == queueSize) {
                    try {
                        println("等待空间")
                        lock.wait()
                    } catch (e: InterruptedException) {
                        lock.notifyAll()
                    }
                }
                queue.offer(1)
                count++
                println("-------添加了数据 + $count")
                lock.notifyAll()
            }
        }
    }
}