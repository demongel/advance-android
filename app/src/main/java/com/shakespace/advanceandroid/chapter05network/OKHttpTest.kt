package com.shakespace.advanceandroid.chapter05network

import android.util.Log
import com.bumptech.glide.RequestBuilder
import com.shakespace.advanceandroid.global.TAG
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.concurrent.TimeUnit

/**
 * created by  shakespace
 * 2021/4/20  19:48
 */


val zhihuApi = "http://news-at.zhihu.com/api/4/news/latest"
val taobaoIPApi = "http://ip.taobao.com/service/getIpInfo.php"// 已经不可用

val upload = "https://api.github.com/markdown/raw"

val download = "http://pic-bucket.ws.126.net/photo/0001/2021-04-22/G865ID5M00AN0001NOS.jpg"

val client = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .writeTimeout(20, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
//    .cache(Cache(get))
    .build()

val callback = object : Callback {
    override fun onFailure(call: Call, e: IOException) {
        println(e.localizedMessage)
    }

    override fun onResponse(call: Call, response: Response) {
        println(response.body()?.string())
    }
}

fun main() {
    // get 请求
//    normalGet()

    // post 请求
//    normalPost()

    // 上传文件
//    uploadFile()

    // 下载文件
    downloadFile()

    uploadMultipart()
}

fun uploadMultipart() {

    val multipartBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("title", "build.gradle")
        .addFormDataPart(
            "body",
            "pic.png",
            RequestBody.create(MediaType.parse("image/png"), File("pic.png"))
        ).build()

    val request = Request.Builder().header("authorization", "Client-ID")
        .url("https://www.baidu.com")
        .post(multipartBody)
        .tag("multipart")
        .build()

    client.newCall(request)
        .enqueue(callback)
}

fun downloadFile() {
    val downloadRequest = Request.Builder().url(download).build()
    val start = System.currentTimeMillis()
    client.newCall(downloadRequest)
        .enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.localizedMessage)
                val tag = call.request().tag()
                // 通过拿到的tag 判断具体是哪个请求
            }

            override fun onResponse(call: Call, response: Response) {
                val byteStream = response.body()?.byteStream()
                val file = File("pic.png")
                val fileOutputStream = FileOutputStream(file)
                byteStream?.apply {
                    try {
                        var len = 0
                        val buffer = ByteArray(2048 * 2000)
                        while (byteStream.read(buffer).also {
                                len = it
                            } != -1) {
                            fileOutputStream.write(buffer, 0, len)
                        }
                        fileOutputStream.flush()
                        byteStream.close()
                        fileOutputStream.close()
                    } catch (e: Exception) {
                        println(e.message)
                    }
                    println((System.currentTimeMillis() - start))
                }
            }
        })
}

fun uploadFile() {
    val file = File("build.gradle")
//    MediaType.parse("text/x-markdown;charset=urf-8")
//    MediaType.parse("build.gradle")
    val uploadRequest = Request.Builder()
        .url(upload)
        .post(RequestBody.create(MediaType.parse("text/x-markdown;charset=urf-8"), file))
        .build()

    // upload 返回的是上传的内容
    client.newCall(uploadRequest)
        .enqueue(callback)

}

fun normalPost() {
    val form = FormBody.Builder()
        .add("ip", "12.4.12.4")
        .build()

    val postRequest = Request.Builder().url(taobaoIPApi)
        .method("POST", form)
        .build()

    client.newCall(postRequest)
        .enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                println(response.body()?.string())
            }

        })
}

/**
 *GET请求
 * 创建一个request
 * 创建OKhttpClient
 * newCall(request) 拿到call对象
 * enqueue 异步， execute同步
 *
 * 注意拿到json结果的方法是 string(), 不是toString()
 *
 * 另外，使用异步方式，程序不会结束
 */
fun normalGet() {
    val asyncGetRequest = Request.Builder().url(zhihuApi)
        .method("GET", null)
        .build()

    val newCall = client.newCall(asyncGetRequest)
//    val execute = newCall.execute()
//    println(execute.body()?.string())
    newCall.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println({ e.localizedMessage })
        }

        override fun onResponse(call: Call, response: Response) {
            println(response.body()?.string())
        }
    })
}