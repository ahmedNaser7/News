package com.example.news.repository.newsRepository

import com.example.news.data.api.model.newsResponse.News

interface NewsRepository {
    suspend fun getNews(sourceId: String): List<News?>?
}