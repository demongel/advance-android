package com.shakespace.advanceandroid.chapter04thread

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * created by  shakespace
 * 2021/4/14  21:00
 */

@Volatile
var vol: Int = 0
var nor = 0
var atomic = AtomicInteger(0)

/**
 * AtomicInteger 可以保证原子性
 * volatile 不能保证原子性
 *  volatile方式的i++，总共是四个步骤：i++实际为load、Increment、store、Memory Barriers 四个操作。
 *
 *  内存屏障是线程安全的,但是内存屏障之前的指令并不是.在某一时刻线程1将i的值load取出来，
 *  放置到cpu缓存中，然后再将此值放置到寄存器A中，然后A中的值自增1（寄存器A中保存的是中间值，
 *  没有直接修改i，因此其他线程并不会获取到这个自增1的值）。如果在此时线程2也执行同样的操作，
 *  获取值i==10,自增1变为11，然后马上刷入主内存。此时由于线程2修改了i的值，
 *  实时的线程1中的i==10的值缓存失效，重新从主内存中读取，变为11。接下来线程1恢复。
 *  将自增过后的A寄存器值11赋值给cpu缓存i。这样就出现了线程安全问题。
 *
 * 简单来说，可见性只保证了我读取时拿到的一定是最新的，但如果读取后，线程被抢走，别的线程完成了自增并写入主存，
 * 回到该线程后并不会再次读取，而是继续之前未完成的操作，也是自增，存入主内存，但是经过两次自增，只增加了1
 *
 * 使用 volatile 关键字会强制将修改的值立即写入主存；
 * 使用 volatile 关键字的话，当线程 2 进行修改时，会导致线程 1 的工作内存中缓存变量 stop 的缓存行无效（反映到硬件层的话，
 * 就是CPU 的 L1 或者 L2 缓存中对应的缓存行无效）；
 *
 * volatile 可以保证有序性
 *  在volatile之前的不会后执行，在之后的也不会先执行
 *
 *
 *  volatile 使用：
 *  （1） 对变量的写操作不会依赖于当前值。
 *（2） 该变量没有包含在具有其他变量的不变式中。
 *  1. 就是不能是自增自减等与自身当前值先关的操作
 *  2. 假设有两个值， A和B， 都用volatile修饰，在setA 和 setB 的方法里都会先验证 A<B
 *      在多线程的情况下，存在分别set都符合，但是结果 A > B 的情况
 *
 *  使用场景：
 *  1. 状态标记
 *  2. 双重检查
 */
fun main() {
    for (i in 1..100) {
        AtomThread().start()
    }

    TimeUnit.MILLISECONDS.sleep(1000)
    println(vol)
    println(nor)
    println(atomic.get())
    /**
     *9989
     *9987
     *10000
     */
}

class AtomThread : Thread() {
    override fun run() {
        for (i in 1..100) {
            vol++
            nor++
            atomic.getAndIncrement()
        }
    }
}