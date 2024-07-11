package com.example.news_data.network

import com.example.common_utils.Constant
import com.example.news_data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    //https://newsapi.org/v2/top-headlines?country=us&apiKey=ce77d840fc094a6db1efdda5aa3cca6a

    @GET("top-headlines")
    suspend fun getNewsArticles(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = Constant.API_KEY
    ): NewsResponse
}