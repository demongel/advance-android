package com.shakespace.advanceandroid.chapter10.mvp.api

import com.shakespace.advanceandroid.chapter10.mvp.model.IpInfo
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface Api {
//    @GET("service/getIpInfo.php")
//    fun getIp(@Query("ip") ip: String): Observable<IpInfo>

    @FormUrlEncoded
    @POST("service/getIpInfo.php")
    fun getIp(@Field("ip") ip: String): Observable<IpInfo>
}


val ipRetrofit = Retrofit.Builder().baseUrl("http://ip.taobao.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient.Builder().addNetworkInterceptor(HttpLoggingInterceptor()).build())
    .build()

val ipService = ipRetrofit.create(Api::class.java)