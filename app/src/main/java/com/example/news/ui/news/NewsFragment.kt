package com.example.news.ui.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.ViewError
import com.example.news.api.model.newsResponse.News
import com.example.news.api.model.sourcesResponse.Source
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.showMessage
import com.example.news.ui.category.CategoryDataClass
import com.example.news.ui.newsDetails.NewsDetailsActivity
import com.google.android.material.tabs.TabLayout


class NewsFragment : Fragment() {

    lateinit var viewBinding: FragmentNewsBinding
    lateinit var viewModel: NewsViewModel
    lateinit var sourceObj: Source
    var isLoading = false
    var pageSize = 20
    var curpage = 1
    lateinit var category: CategoryDataClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    companion object {
        fun getInstance(category: CategoryDataClass): NewsFragment {
            var newNewsFragment = NewsFragment()
            newNewsFragment.category = category
            return newNewsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsBinding.inflate(
            inflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        viewModel.getNewsSources(category)
    }

    private fun initObservers() {
        viewModel.shouldShowLoading.observe(viewLifecycleOwner) { isVisible ->
            viewBinding.progressBar.isVisible = isVisible
        }
        viewModel.sourcesLiveData.observe(viewLifecycleOwner) { sources ->
            bindTabs(sources)
            getNews()
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner) { news ->
            adapter.bindNews(news)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            handleError(it)
        }
        viewModel.newsSearchLiveData.observe(viewLifecycleOwner) {
            adapter.bindNews(it)
        }
    }

    private fun getNews() {
        viewModel.getNews(sourceObj.id, pageSize = pageSize, page = curpage)
        isLoading = false
    }

    val adapter = NewsAdapter()
    private fun initViews() {
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this // connect fragment lifeCycle to xml bec we use liveData
        viewBinding.recyclerView.adapter = adapter
        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiableItemCount = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                val visiableThreshold = 3
                if (!isLoading && totalItemCount - lastVisiableItemCount >= visiableThreshold) {
                    isLoading = true
                    curpage++
                    getNews()
                }
            }
        })
        adapter.onItemClickListener = object : NewsAdapter.OnItemClickListener {
            override fun onCLickItem(news: News?) {
                val intent = Intent(requireContext(), NewsDetailsActivity::class.java)
                intent.putExtra("news", news)
                startActivity(intent)
            }

        }
        //search
        val search = viewBinding.searchView
        search.clearFocus()
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchNews(
                    sourceObj.id,
                    pageSize = pageSize,
                    page = curpage,
                    query ?: ""
                )
                Log.e("com.example.news", "not working", Throwable())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })
    }


    private fun bindTabs(sources: List<Source?>?) {
        sources?.forEach { source ->
            val tab = viewBinding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source // use as index of source but his type is source
            viewBinding.tabLayout.addTab(tab)

            var layputParams = LinearLayout.LayoutParams(tab.view.layoutParams)
            layputParams.marginEnd = 12
            layputParams.marginStart = 12
            layputParams.topMargin = 18
            tab.view.layoutParams = layputParams
        }
        viewBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    sourceObj = source
                    viewModel.getNews(source.id, pageSize = pageSize, page = curpage)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    sourceObj = source
                    viewModel.getNews(source.id, pageSize = pageSize, page = curpage)
                }

            }
        )
        viewBinding.tabLayout.getTabAt(0)?.select()
    }


    fun handleError(viewError: ViewError) {
        showMessage(
            message = viewError.message ?: viewError.throwable?.localizedMessage
            ?: "something went wrong",
            posActionName = "try Again",
            posAction = { dialog, which ->
                dialog.dismiss() // to not show dialog
                viewError.onTryAgainClickListener?.onTryAgainClick()
            }, negActionName = "cancel",
            negAction = { dialog, which ->
                dialog.dismiss()
            }

        )
    }
}