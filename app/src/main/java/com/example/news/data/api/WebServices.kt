package com.example.news.data.api

import com.example.news.data.api.model.newsResponse.NewsResponse
import com.example.news.data.api.model.sourcesResponse.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources") // api name
    suspend fun getSources(
        @Query("apiKey") key: String = ApiConstants.apiKey,// Required parameters
        @Query("category") category: String,
    ): SourcesResponse // return response of api

    @GET("v2/everything") // api name
    suspend fun getNews(
        @Query("apiKey") key: String = ApiConstants.apiKey,// Required parameters
        @Query("sources") sources: String,
    ): NewsResponse // return response of api

}