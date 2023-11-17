package com.example.news.data.Repository

import com.example.news.data.dataSource.NewsOnlineDataSourceImpl
import com.example.news.data.dataSource.SourcesOnlineDataSourceImpl
import com.example.news.dataSource.NewsDataSource
import com.example.news.dataSource.SourcesDataSource
import com.example.news.repository.newsRepository.NewsRepository
import com.example.news.repository.sourcesRepository.SourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun provideSourcesRepository(
        repo: SourcesRepositoryImpl
    ): SourcesRepository

    @Binds
    abstract fun provideSourcesDataSource(
        dataSource: SourcesOnlineDataSourceImpl
    ): SourcesDataSource

    @Binds
    abstract fun provideNewsRepository(
        repo: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    abstract fun provideNewsDataSource(
        dataSource: NewsOnlineDataSourceImpl
    ): NewsDataSource


}