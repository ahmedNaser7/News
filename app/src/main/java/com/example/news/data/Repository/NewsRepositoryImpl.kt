package com.example.news.data.Repository

import com.example.news.data.api.model.newsResponse.News
import com.example.news.dataSource.NewsDataSource
import com.example.news.repository.newsRepository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsOnlineDataSource: NewsDataSource) :
    NewsRepository {
    override suspend fun getNews(sourceId: String): List<News?>? {
        return newsOnlineDataSource.getNews(sourceId)
    }
}