package com.shakespace.advanceandroid.chapter08rxjava

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.internal.observers.BasicFuseableObserver
import io.reactivex.internal.operators.observable.ObservableMap
import io.reactivex.observables.GroupedObservable
import java.util.*

/**
 * 变换操作符
 */

fun main() {
    doRx2()

    try {
        Thread.sleep(10000)
    } catch (e: Exception) {
        println(e.message)
    }
}

@SuppressLint("CheckResult")
fun doRx2() {
    /**
     * map 直接转换，接收的函数 参数是T 返回值是R
     */
    val host = "http://blog.csdn.net/"
    Observable.just("name").map { s ->
        host + s
    }.subscribe {
        println("map : $it")
    }

    /**
     * flatMap 用于多个数据或者连续处理，接收的函数，参数是T, 返回值是 ObservableSource<R>
     * 返回值经过 flatMap 展开，订阅时拿到的是普通对象
     *
     * 通过集合发送数据使用 fromIterable
     *
     * cast 强制转换类型，cast操作时 会进行类型检查
     */
    val list: MutableList<String> = ArrayList()
    list.add("name1")
    list.add("name2")
    list.add("name3")
    list.add("name4")

    Observable.fromIterable(list).flatMap { s ->
        Observable.just(host + s)
    }.cast(String::class.java)
        .subscribe {
            println("flatMap & cast : $it")
        }

    /**
     * concatMap 保持原有顺序， flatmap 不一定能保持原有顺序
     */
    Observable.fromIterable(list).concatMap { s ->
        Observable.just(host + s)
    }.cast(String::class.java)
        .subscribe {
            println("concatMap & cast : $it")
        }

    /**
     * flatMapIterable操作符可以将数据包装成Iterable
     */
    Observable.just(1, 2, 3).flatMapIterable {
        val mutableList = mutableListOf<Int>()
        mutableList.add(it + 1)
        mutableList
    }.subscribe {
        println("flatMapIterable  : $it")
    }


    /**
     * buffer操作符将源Observable变换为一个新的Observable，这个新的Observable每次发射一组列表值而不 是一个一个发射。
     * buffer(3)的意思是缓存容量为3 ， 意思是只要后面还有值，就存到三个再发射数据
     */
    Observable.just(1, 2, 3, 4, 5, 6, 7)
        .buffer(3)
        .subscribe { bufferList ->
            for (item: Int in bufferList) {
                println("buffer  : $item")
            }
            println("buffer -----------")
        }

    /**
     * 将数据按照要求进行分组
     */
    val s1 = Swordsman("韦一笑", "A")
    val s2 = Swordsman("张三丰", "SS")
    val s3 = Swordsman("周芷若", "S")
    val s4 = Swordsman("宋远桥", "S")
    val s5 = Swordsman("殷梨亭", "A")
    val s6 = Swordsman("张无忌", "SS")
    val s7 = Swordsman("鹤笔翁", "S")
    val s8 = Swordsman("宋青书", "A")

    val groupedObservable: Observable<GroupedObservable<String, Swordsman>> =
        Observable.just(s1, s2, s3, s4, s5, s6, s7, s8)
            .groupBy {
                it.level
            }
    Observable.concat(groupedObservable).subscribe {
        println(("groupBy:" + it.name + "---" + it.level))
    }
}
