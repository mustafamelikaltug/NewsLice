package com.mustafamelikaltug.newslice.service

import com.mustafamelikaltug.newslice.model.Response
import io.reactivex.Single
import retrofit2.http.GET

interface NewsAPI {
    @GET("v4/search?q=example&lang=en&country=us&max=10&apikey=YOUR_API_KEY")
    fun getNews():Single<Response>
}