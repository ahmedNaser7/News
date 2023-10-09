package com.example.news.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.news.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    lateinit var viewBinding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCategoryBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = CategoryAdapter(CategoryDataClass.getCategoryList())
        viewBinding.recyclerView.adapter = adapter
        adapter.onItemClickListener = object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: CategoryDataClass) {
                onCategoryClickListener?.onCategoryClick(item)
            }

        }

    }

    var onCategoryClickListener: OnCategoryClickListener? = null

    interface OnCategoryClickListener {
        fun onCategoryClick(category: CategoryDataClass)
    }
}