package com.example.news.data.dataSource

import com.example.news.data.api.WebServices
import com.example.news.data.api.model.sourcesResponse.Source
import com.example.news.dataSource.SourcesDataSource

class SourcesOnlineDataSourceImpl(val webServices: WebServices) : SourcesDataSource {
    override suspend fun getSources(category: String): List<Source?>? {
        return webServices.getSources(category = category).sources
    }

}