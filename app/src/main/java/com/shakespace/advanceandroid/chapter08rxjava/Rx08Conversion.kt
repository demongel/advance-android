package com.shakespace.advanceandroid.chapter08rxjava

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable

/**
 * 转换操作符
 */

fun main() {
    doRx8()

    try {
        Thread.sleep(10000)
    } catch (e: Exception) {
        println(e.message)
    }
}

@SuppressLint("CheckResult")
fun doRx8() {

    /**
     * toList操作符将发射多项数据且为每一项数据调用onNext方法的Observable发射的多项数据组合成一个 List，
     * 然后调用一次onNext方法传递整个列表。
     */
    Observable.just(1, 2, 3).toList()
        .subscribe({ list ->
            for (it in list) {
                println("toList $it")
            }
        }, {
            println("toList error ${it.message}")
        }
        )

    /**
     * toSortedList操作符类似于toList操作符;不同的是，它会对产生的列表排序，默认是自然升序。如果发射的数据项没有实现Comparable接口，会抛出一个异常。
     * 当然，若发射的数据项没有实现Comparable接口， 可以使用toSortedList(Func2)变体，其传递的函数参数Func2会作用于比较两个数据项。
     */
    Observable.just(3, 1, 2).toSortedList().subscribe({ list ->
        for (it in list) {
            println("toSortedList $it")
        }
    }, {
        println("toSortedList error ${it.message}")
    }
    )

    /**
     * toMap操作符收集原始Observable发射的所有数据项到一个Map(默认是HashMap)，然后发射这个 Map。
     * 可以提供一个用于生成Map的key的函数，也可以提供一个函数转换数据项到Map存储的值(默认数据项本身就是值)。
     */
    val s1 = Swordsman("韦一笑", "A")
    val s2 = Swordsman("张三丰", "SS")
    val s3 = Swordsman("周芷若", "S")
    Observable.just(s1, s2, s3).toMap {
        it.level // 作为key
    }.subscribe({ map ->
        println("map : ${map["SS"]?.name}")
    }, {
        println("map error ${it.message}")
    })
}


