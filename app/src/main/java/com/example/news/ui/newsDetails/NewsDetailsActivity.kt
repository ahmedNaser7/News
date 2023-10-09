package com.example.news.ui.newsDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.news.api.model.newsResponse.News
import com.example.news.databinding.ActivityNewsDetailsBinding

class NewsDetailsActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityNewsDetailsBinding
    private lateinit var news: News
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        news = ((intent.getParcelableExtra("news") as? News)!!)
        viewBinding.newsData = news
    }
}