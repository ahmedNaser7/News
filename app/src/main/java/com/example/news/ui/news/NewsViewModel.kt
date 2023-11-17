package com.example.news.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.api.model.newsResponse.News
import com.example.news.data.api.model.newsResponse.NewsResponse
import com.example.news.data.api.model.sourcesResponse.Source
import com.example.news.data.api.model.sourcesResponse.SourcesResponse
import com.example.news.repository.newsRepository.NewsRepository
import com.example.news.repository.sourcesRepository.SourcesRepository
import com.example.news.ui.ViewError
import com.example.news.ui.category.CategoryDataClass
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val sourcesRepository: SourcesRepository,
    val newsRepository: NewsRepository
) : ViewModel() {

    val shouldShowLoading = MutableLiveData<Boolean>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()
    val errorLiveData = MutableLiveData<ViewError>()


    fun getNewsSources(categoryDataClass: CategoryDataClass) {

        viewModelScope.launch {
            shouldShowLoading.postValue(true)
            // ((use in main thread))
            try {
                val response = sourcesRepository.getSources(category = categoryDataClass.id)
                sourcesLiveData.postValue(response)
            } catch (e: HttpException) {
                val errorBodyJsonString = e.response()?.errorBody().toString()
                val errorResponse = Gson().fromJson(
                    errorBodyJsonString,
                    SourcesResponse::class.java
                ) // convert from errorResponse to sourcesResponse
                errorLiveData.postValue(
                    ViewError(message = errorResponse.message) {
                        getNewsSources(categoryDataClass)
                    }
                )
            } catch (e: Exception) {
                shouldShowLoading.postValue(false)
                errorLiveData.postValue(
                    ViewError(throwable = e) {
                        getNewsSources(categoryDataClass)
                    }
                )
            } finally {
                shouldShowLoading.postValue(false)
            }


        }

    }

    fun getNews(sourceId: String?) {
        viewModelScope.launch {
            shouldShowLoading.postValue(true)
            try {
                val response = newsRepository.getNews(sourceId ?: "")
                newsLiveData.postValue(response)
            } catch (ex: HttpException) {
                val errorBodyJsonString = ex.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBodyJsonString, NewsResponse::class.java)
                errorLiveData.postValue(
                    ViewError(errorResponse.message) {
                        getNews(sourceId)
                    }
                )
            } catch (e: Exception) {
                shouldShowLoading.postValue(false)
                errorLiveData.postValue(
                    ViewError(throwable = e) {
                        getNews(sourceId)
                    }
                )
            } finally {
                shouldShowLoading.postValue(false)
            }
        }


    }
}
