package com.example.search_data.network

import com.example.search_data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchApi {


    //https://newsapi.org/v2/everything?q=apple&from=2024-07-10&to=2024-07-10&sortBy=popularity&apiKey=ce77d840fc094a6db1efdda5aa3cca6a
    @GET("everything")
    suspend fun getSearchArticle(
        @QueryMap map: MutableMap<String, String>
    ): NewsResponse
}