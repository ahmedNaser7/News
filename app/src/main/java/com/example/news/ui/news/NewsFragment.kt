package com.example.news.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    }

    private fun getNews() {
        viewModel.getNews(sourceObj.id)
        isLoading = false
    }

    val adapter = NewsAdapter()
    private fun initViews() {
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this // connect fragment lifeCycle to xml bec we use liveData
        viewBinding.recyclerView.adapter = adapter
        adapter.onItemClickListener = object : NewsAdapter.OnItemClickListener {
            override fun onCLickItem(news: News?) {
                val intent = Intent(requireContext(), NewsDetailsActivity::class.java)
                intent.putExtra("news", news)
                startActivity(intent)
            }

        }
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
                    viewModel.getNews(source.id)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    sourceObj = source
                    viewModel.getNews(source.id)
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