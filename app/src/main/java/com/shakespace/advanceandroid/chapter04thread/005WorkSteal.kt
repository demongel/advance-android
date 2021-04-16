package com.shakespace.advanceandroid.chapter04thread

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.*

/**
 * created by  shakespace
 * 2021/4/16  15:31
 */

@RequiresApi(Build.VERSION_CODES.N)
@Throws(ExecutionException::class, InterruptedException::class)
fun main() {
    // 构造数据
    val length = 100000000
    val arr = LongArray(length)
    for (i in 0 until length) {
        arr[i] = ThreadLocalRandom.current().nextInt(Int.MAX_VALUE).toLong()
    }
    // 单线程
    singleThreadSum(arr)
    // ThreadPoolExecutor线程池
    multiThreadSum(arr)
    // ForkJoinPool线程池
    forkJoinSum(arr)
}

private fun singleThreadSum(arr: LongArray) {
    val start = System.currentTimeMillis()
    var sum: Long = 0
    for (i in arr.indices) {
        // 模拟耗时
        sum += arr[i] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3
    }
    println("sum: $sum")
    println("single thread elapse: " + (System.currentTimeMillis() - start))
}

@Suppress("UNCHECKED_CAST")
@Throws(ExecutionException::class, InterruptedException::class)
private fun multiThreadSum(arr: LongArray) {
    val start = System.currentTimeMillis()
    val count = 8
    val threadPool: ExecutorService = Executors.newFixedThreadPool(count)
    val list: MutableList<Future<Long>> = ArrayList()
    for (i in 0 until count) {
        // 分段提交任务
        val future: Future<Long> = threadPool.submit<Long> {
            var sum: Long = 0
            for (j in arr.size / count * i until arr.size / count * (i + 1)) {
                try {
                    // 模拟耗时
                    sum += arr[j] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            sum
        }
        list.add(future)
    }

    // 每个段结果相加
    var sum: Long = 0
    for (future: Future<Long> in list) {
        sum += future.get()
    }
    println("sum: $sum")
    println("multi thread elapse: " + (System.currentTimeMillis() - start))
    threadPool.shutdown()
}

@RequiresApi(Build.VERSION_CODES.N)
@Throws(ExecutionException::class, InterruptedException::class)
private fun forkJoinSum(arr: LongArray) {
    val start = System.currentTimeMillis()
    val forkJoinPool: ForkJoinPool = ForkJoinPool.commonPool()
    // 提交任务
    val forkJoinTask: ForkJoinTask<Long?>? = forkJoinPool.submit(SumTask(arr, 0, arr.size))
    // 获取结果
    val sum: Long? = forkJoinTask?.get()
    forkJoinPool.shutdown()
    println("sum: $sum")
    println("fork join elapse: " + (System.currentTimeMillis() - start))
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
private class SumTask(private val arr: LongArray, private val from: Int, private val to: Int) :
    RecursiveTask<Long?>() {
    protected override fun compute(): Long? {
        // 小于1000的时候直接相加，可灵活调整
        if (to - from <= 1000) {
            var sum: Long = 0
            for (i in from until to) {
                // 模拟耗时
                sum += arr[i] / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3 / 3 * 3
            }
            return sum
        }

        // 分成两段任务
        val middle = (from + to) / 2
        val left = SumTask(arr, from, middle)
        val right = SumTask(arr, middle, to)

        // 提交左边的任务
        left.fork()
        // 右边的任务直接利用当前线程计算，节约开销
        val rightResult = right.compute()
        // 等待左边计算完毕
        val leftResult: Long? = left.join()
        // 返回结果
        return leftResult!! + rightResult!!
    }
}
