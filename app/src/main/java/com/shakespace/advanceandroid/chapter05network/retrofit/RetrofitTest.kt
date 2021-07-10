package com.shakespace.advanceandroid.chapter05network.retrofit

import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * created by  shakespace
 * 2021/5/18  11:07
 */

// 尝试传入搜索关键字时，拿到一个 location.replace(location.href.replace("https://","http://")); 的页面
// 换成http 则正常
val retrofit = Retrofit.Builder().baseUrl("http://www.baidu.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

// 参见 http://jsonplaceholder.typicode.com/guide/
val retrofit2 = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

fun main() {

    val ipService = retrofit.create(IpService::class.java)

    val jsonService = retrofit2.create(IpService::class.java)
    // post  使用field方式
//    val subscribe = jsonService.getResource("kotlin", "android", "100").subscribe(
//        {
//            println(it.string())
//        },
//        {
//            println(it.message)
//        })

    // 使用body方式 效果和使用field是一样的
    /*
    {
  "title": "kotlin2",
  "body": "android2",
  "userid": "101",
  "id": 101
}
     */
//    val params = hashMapOf("title" to "kotlin2", "body" to "android2", "userid" to "101")
//    val subscribe = jsonService.getResource(
//        RequestBody.create(
//            MediaType.parse("application/json;charset=utf-8"),
//            Gson().toJson(params)
//        )
//    ).subscribe(
//        {
//            println(it.string())
//        },
//        {
//            println(it.message)
//        })

    // body 直接传入一个dataclass 也是一样的效果
    val subscribe = jsonService.getResource(
        User("kotlin", "android", "103")
    ).subscribe(
        {
            println(it.string())
        },
        {
            println(it.message)
        })

// 普通GET请求
//    val subscribe = ipService.getBaidu().subscribe(
//        {
//            println(it.string())
//        },
//        {
//            println(it.message)
//        })

// 传入参数
//    val subscribe = ipService.getBaidu2("s").subscribe(
//        {
//            println(it.string())
//        },
//        {
//            println(it.message)
//        })

// 传入参数 报错
//    val subscribe = ipService.getBaidu3("s").subscribe(
//        {
//            println(it.string())
//        },
//        {
//            println(it.message)
//        })

// 普通query
//    val subscribe = ipService.getBaidu4("utf-8", "测试").subscribe(
//        {
//            println(it.string())
//        },
//        {
//            println(it.message)
//        })

// query map
//    val params = hashMapOf("ie" to "utf-8", "wd" to "android")
//    val subscribe = ipService.getBaidu4(params).subscribe(
//        {
//            println(it.string())
//        },
//        {
//            println(it.message)
//        })

    subscribe.dispose()
}

data class User(val title: String, val body: String, val userid: String)