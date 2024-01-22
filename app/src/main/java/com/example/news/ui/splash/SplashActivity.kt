package com.example.news.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.news.databinding.ActivitySplashBinding
import com.example.news.ui.news.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()
    }

    private fun initView() {
        Handler().postDelayed(
            {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            },
            2000
        )
    }
}