package com.example.news.data.Repository

import com.example.news.data.api.model.sourcesResponse.Source
import com.example.news.dataSource.SourcesDataSource
import com.example.news.repository.sourcesRepository.SourcesRepository
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor(private val sourcesOnlineDataSource: SourcesDataSource) :
    SourcesRepository {
    override suspend fun getSources(category: String): List<Source?>? {
        return sourcesOnlineDataSource.getSources(category)
    }
}