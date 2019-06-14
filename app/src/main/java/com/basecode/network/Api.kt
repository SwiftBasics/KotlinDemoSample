package com.basecode.network

import com.basecode.model.Post
import io.reactivex.Observable
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface Api {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

    @POST("common/splaceinfo")
    fun getPostMethod(@Body body: RequestBody): Observable<JSONObject>

}