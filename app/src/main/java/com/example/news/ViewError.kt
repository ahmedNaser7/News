package com.example.news

data class ViewError(
    val message: String? = null,
    val throwable: Throwable? = null,
    val onTryAgainClickListener: OnTryAgainClickListener? = null,
)

fun interface OnTryAgainClickListener {
    fun onTryAgainClick() // to callback the getNews() or getNewsSources()
}