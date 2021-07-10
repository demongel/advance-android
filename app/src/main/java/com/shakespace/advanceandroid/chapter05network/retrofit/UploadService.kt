package com.shakespace.advanceandroid.chapter05network.retrofit

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 * created by  shakespace
 * 2021/7/9  23:03
 */
interface UploadService {
    /**
     * Multipart注解表示允许多个@Part。 updateUser方法的第一个参数是准备上传的图片文件， 使用了
     * MultipartBody.Part类型； 另一个参数是RequestBody类型， 它用来传递简单的键值对。
     * 多个文件也可以使用PartMap
     */

    // 使用
//    File file = new File(path);
//    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//    name 后端固定是 "file"
//    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//    mUploadService.uploadImage(body)
    @Multipart
    @POST("upload/photo")
    fun uploadImage(@Part part: MultipartBody.Part): Observable<ResponseBody>

    @Multipart
    @POST("upload/photo")
    fun uploadImage(
        @Part part: MultipartBody.Part,
        @Part("desc") requestBody: RequestBody
    ): Observable<ResponseBody>

    @Multipart
    @POST("upload/photo")
    fun uploadImage(
        @PartMap photos: Map<String, RequestBody>,
        @Part("desc") requestBody: RequestBody
    ): Observable<ResponseBody>

    // 添加header 两种方式，使用headers 静态添加，使用header 动态添加
    @GET("haha")
    @Headers("Accept-Encoding:application/json")
    fun getFromTaobao(): Observable<ResponseBody>

    @GET("haha")
    @Headers("Accept-Encoding:application/json", "User-Agent:moon")
    fun getFromTaobao2(): Observable<ResponseBody>

    @GET("haha")
    @Headers("Accept-Encoding:application/json", "User-Agent:moon")
    fun getFromTaobao3(@Header("id") id: String): Observable<ResponseBody>
}