package com.example.news.ui.news

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class di {

    @Provides
    fun provideNewsAdapter(): NewsAdapter {
        return NewsAdapter(listOf())
    }
}