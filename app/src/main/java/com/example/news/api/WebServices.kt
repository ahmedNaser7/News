package com.example.news.api

import com.example.news.api.model.newsResponse.NewsResponse
import com.example.news.api.model.sourcesResponse.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources") // api name
    fun getSources(
        @Query("apiKey") key: String = ApiConstants.apiKey,// Required parameters
        @Query("category") category: String,
    ): Call<SourcesResponse> // return response of api

    @GET("v2/everything") // api name
    fun getNews(
        @Query("apiKey") key: String = ApiConstants.apiKey,// Required parameters
        @Query("sources") sources: String,
    ): Call<NewsResponse> // return response of api
}