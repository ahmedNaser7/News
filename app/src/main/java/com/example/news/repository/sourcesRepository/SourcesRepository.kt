package com.example.news.repository.sourcesRepository

import com.example.news.data.api.model.sourcesResponse.Source

interface SourcesRepository {
    suspend fun getSources(category: String): List<Source?>?
}