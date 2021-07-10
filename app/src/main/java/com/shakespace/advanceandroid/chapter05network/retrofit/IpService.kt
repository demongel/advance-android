package com.shakespace.advanceandroid.chapter05network.retrofit

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * created by  shakespace
 * 2021/5/18  10:50
 */
interface IpService {

    // 没有参数要加 “/”
    @GET("/")
    fun getBaidu(): Observable<ResponseBody>

    // Path 动态配置URL 地址
    @GET("{path}?ie=utf-8&wd=1")
    fun getBaidu2(@Path("path") searchWord: String): Observable<ResponseBody>

    // Path 动态配置URL 地址
    // java.lang.IllegalArgumentException: URL query string "ie=utf-8&wd={path}" must not have replace block. For dynamic query parameters use @Query.
    // query 参数中不应包含 可变的{}参数  见getBaidu4
    @GET("s?ie=utf-8&wd={path}")
    fun getBaidu3(@Path("path") searchWord: String): Observable<ResponseBody>

    @GET("s")
    fun getBaidu4(
        @Query("ie") chartSet: String,
        @Query("wd") keyword: String
    ): Observable<ResponseBody>

    @GET("s")
    fun getBaidu4(
        @QueryMap params: Map<String, String>
    ): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("posts")
    fun getResource(
        @Field("title") title: String,
        @Field("body") body: String,
        @Field("userid") userid: String
    ): Observable<ResponseBody>

    @POST("posts")
    fun getResource(
        @Body requestBody: RequestBody
    ): Observable<ResponseBody>

    @POST("posts")
    fun getResource(
        @Body user: User
    ): Observable<ResponseBody>

}