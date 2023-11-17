package com.example.news.data.dataSource

import com.example.news.data.api.WebServices
import com.example.news.data.api.model.newsResponse.News
import com.example.news.dataSource.NewsDataSource

class NewsOnlineDataSourceImpl(val webServices: WebServices) : NewsDataSource {
    override suspend fun getNews(sourceId: String): List<News?>? {
        return webServices.getNews(sources = sourceId).articles
    }

}