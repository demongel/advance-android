package com.shakespace.advanceandroid.chapter04thread

import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * created by  shakespace
 * 2021/4/14  16:43
 */

fun main() {
    val lock = ReentrantLock()
    lock.lock()
    try {

    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        lock.unlock()
    }

}

open class Alipay(val n: Int, val money: Double) {
    private var accounts: DoubleArray = DoubleArray(n)
    private var alipayLock = ReentrantLock()
    private lateinit var condition: Condition

    init {
        for (i in 0..n) {
            accounts[i] = money
        }
        // 获取一个条件对象
        condition = alipayLock.newCondition()
    }

    fun transfer(from: Int, to: Int, amount: Int) {
        alipayLock.lock()
        try {
            // 如果账户余额不足，转账无法进行
            while (accounts[from] < amount) {
                // 调用条件对象，阻塞当前线程，并放弃锁
                // 通常是消费者消费完了，需要等待生产者时挂起,生产者产生新的对象后唤醒
                condition.await()
            }
            accounts[from] = accounts[from] - amount
            accounts[to] = accounts[to] + amount
            // 可以放在外部根据条件唤醒
            condition.signalAll()
        } finally {
            alipayLock.unlock()
        }
    }

    private val lock = Object()
    fun lock() {
        synchronized(lock) {
            // 同步代码块
        }
    }

}