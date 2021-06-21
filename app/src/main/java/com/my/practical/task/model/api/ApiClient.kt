package com.my.practical.task.model.api

import com.my.practical.task.model.responses.DataResponse
import com.my.practical.task.model.responses.UserData
import com.my.practical.task.model.responses.UserRepoData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    companion object {
        operator fun invoke(): ApiClient {
            val client = OkHttpClient
                .Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.github.com/") // API Root path
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiClient::class.java)
        }
    }

    @Headers("content-type: application/json; charset=utf-8")
    @GET("search/users")
    fun getUserSearch(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Observable<DataResponse>

    @Headers("content-type: application/json; charset=utf-8")
    @GET("users/{user}")
    fun getUserInfo(@Path("user") user_name: String): Observable<UserData>

    @Headers("content-type: application/json; charset=utf-8")
    @GET("users/{user}/repos")
    fun getUserRipo(@Path("user") user_name: String): Observable<List<UserRepoData>>

}
