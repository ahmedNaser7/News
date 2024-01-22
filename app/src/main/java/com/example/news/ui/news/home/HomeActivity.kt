package com.example.news.ui.news.home

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.news.R
import com.example.news.databinding.ActivityHomeBinding
import com.example.news.ui.category.CategoryDataClass
import com.example.news.ui.category.CategoryFragment
import com.example.news.ui.news.NewsFragment
import com.example.news.ui.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), CategoryFragment.OnCategoryClickListener {
    lateinit var viewBinding: ActivityHomeBinding
    var categoryFragment = CategoryFragment()

    override fun onCategoryClick(category: CategoryDataClass) {
        showCategoryDetailsFragment(category)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        categoryFragment.onCategoryClickListener = this // to use in home activity

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, categoryFragment)
            .commit()

        // fun show navDrawer
        showToggelNavDrawer()

    }

    fun showCategoryDetailsFragment(category: CategoryDataClass) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NewsFragment.getInstance(category))
            .addToBackStack(null)
            .commit()


    }

    fun showToggelNavDrawer() {
        val toggel = ActionBarDrawerToggle(
            this,
            viewBinding.myDrawer,
            viewBinding.toolbar,
            R.string.open,
            R.string.close
        )
        viewBinding.myDrawer.addDrawerListener(toggel)
        toggel.syncState()
        viewBinding.navView.setNavigationItemSelectedListener { items ->
            when (items.itemId) {
                R.id.nav_category -> {
                    showCategoryFragment()
                }

                R.id.nav_setting -> {
                    showSettingFragment()
                }
            }
            viewBinding.myDrawer.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    fun showCategoryFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CategoryFragment())
            .addToBackStack(null)
            .commit()
    }

    fun showSettingFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, SettingFragment())
            .addToBackStack(null)
            .commit()
    }
}