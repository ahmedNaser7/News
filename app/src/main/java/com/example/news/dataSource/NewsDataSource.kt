package com.example.news.dataSource

import com.example.news.data.api.model.newsResponse.News

interface NewsDataSource {
    suspend fun getNews(sourceId: String): List<News?>?
}