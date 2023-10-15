package com.mustafamelikaltug.newslice.service

import com.mustafamelikaltug.newslice.model.Response
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAPIService {

    private val BASE_URL = "https://gnews.io/api/"
    private val api = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    fun getData() : Single<Response> {
        return api.getNews()
    }
}