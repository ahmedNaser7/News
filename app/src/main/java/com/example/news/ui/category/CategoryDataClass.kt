package com.example.news.ui.category

import com.example.news.R


data class CategoryDataClass(
    var id: String,
    var name: String,
    var imageId: Int,
    var backgroundColoId: Int,
) {

// general

    companion object {
        fun getCategoryList(): List<CategoryDataClass> {
            return listOf(
                CategoryDataClass("sports", "sports", R.drawable.sports, R.color.red),
                CategoryDataClass(
                    "entertainment",
                    "entertainment",
                    R.drawable.politics,
                    R.color.blue
                ),
                CategoryDataClass("health", "health", R.drawable.health, R.color.bink),
                CategoryDataClass("business", "business", R.drawable.bussines, R.color.gold),
                CategoryDataClass(
                    "technology",
                    "technology",
                    R.drawable.environment,
                    R.color.babyBlue
                ),
                CategoryDataClass("science", "science", R.drawable.science, R.color.yellow),
            )
        }
    }
}
