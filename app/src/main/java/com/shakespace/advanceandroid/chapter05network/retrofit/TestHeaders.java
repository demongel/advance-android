package com.shakespace.advanceandroid.chapter05network.retrofit;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Part;

/**
 * created by  shakespace
 * 2021/7/9  23:28
 */
interface TestHeaders {
    @GET("haha")
    @Headers({"a:b", "c:d"})
    Observable<ResponseBody> uploadImage(@Part MultipartBody.Part part);

}
