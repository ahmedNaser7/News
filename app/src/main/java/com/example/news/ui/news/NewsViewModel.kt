package com.example.news.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.ViewError
import com.example.news.api.ApiManager
import com.example.news.api.model.newsResponse.News
import com.example.news.api.model.newsResponse.NewsResponse
import com.example.news.api.model.sourcesResponse.Source
import com.example.news.api.model.sourcesResponse.SourcesResponse
import com.example.news.ui.category.CategoryDataClass
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val shouldShowLoading = MutableLiveData<Boolean>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()
    val errorLiveData = MutableLiveData<ViewError>()


    fun getNewsSources(categoryDataClass: CategoryDataClass) {

        shouldShowLoading.postValue(true)
        // ((use in main thread))
        ApiManager
            .getApis()
            .getSources(category = categoryDataClass.id)
            .enqueue(object : retrofit2.Callback<SourcesResponse> {

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    shouldShowLoading.postValue(false)

                    errorLiveData.postValue(
                        ViewError(throwable = t) {
                            getNewsSources(categoryDataClass)
                        }
                    )
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    shouldShowLoading.postValue(false)
                    if (response.isSuccessful) {
                        sourcesLiveData.postValue(response.body()?.sources)
                    } else {
                        val errorBodyJsonString = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(
                            errorBodyJsonString,
                            SourcesResponse::class.java
                        ) // convert from errorResponse to sourcesResponse
                        errorLiveData.postValue(
                            ViewError(errorResponse.message) {
                                getNewsSources(categoryDataClass)
                            }
                        )
                    }
                }

            })  // show result at the end


    }

    fun getNews(sourceId: String?, pageSize: Int?, page: Int?) {


        shouldShowLoading.postValue(true)
        ApiManager
            .getApis()
            .getNews(sources = sourceId ?: "", pageSize = pageSize ?: 0, page = page ?: 0)
            .enqueue(object : Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    shouldShowLoading.postValue(false)
                    errorLiveData.postValue(
                        ViewError(throwable = t) {
                            getNews(sourceId, pageSize, page)
                        }
                    )
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    shouldShowLoading.postValue(false)
                    if (response.isSuccessful) {
                        // adapter

                        newsLiveData.postValue(response.body()?.articles)
                        //  adapter.bindNews(response.body()?.articles)

                        return
                    }
                    val errorBodyJsonString = response.errorBody()?.string()
                    val errorResponse =
                        Gson().fromJson(errorBodyJsonString, NewsResponse::class.java)
                    errorLiveData.postValue(
                        ViewError(errorResponse.message) {
                            getNews(sourceId, pageSize, page)
                        }
                    )
                }

            })
    }



}
