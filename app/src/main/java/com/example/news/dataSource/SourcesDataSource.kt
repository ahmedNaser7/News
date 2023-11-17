package com.example.news.dataSource

import com.example.news.data.api.model.sourcesResponse.Source

interface SourcesDataSource {
    suspend fun getSources(category: String): List<Source?>?
}