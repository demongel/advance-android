package com.shakespace.advanceandroid.chapter09annotation

import com.shakespace.annotations.BindView

/**
 * 只能用具名参数
 */
@Swordman(name = "Haha", age = 11)
@Man
class World {

    @BindView
    val test: String = ""

    /**
     * Build 项目时会打印 元素名称， 这里是 test
     * > Task :app:kaptDebugKotlin
     * Annotation processors discovery from compile classpath is deprecated.
     * Set 'kapt.includeCompileClasspath = false' to disable discovery.
     * Run the build with '--info' for more details.
     * test
     * Note: print message test
     */
}