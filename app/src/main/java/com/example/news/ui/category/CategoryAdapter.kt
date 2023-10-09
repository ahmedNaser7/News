package com.example.news.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.CategoryItemRecyclerBinding

class CategoryAdapter(val categoryList: List<CategoryDataClass>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    class ViewHolder(val viewBinding: CategoryItemRecyclerBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(categoryDataClass: CategoryDataClass?) {
            viewBinding.category = categoryDataClass
            viewBinding.invalidateAll()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewBinding = CategoryItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = categoryList.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = categoryList[position]
        holder.bind(item)
        onItemClickListener?.let {
            holder.viewBinding.layoutItem.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }
        }
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: CategoryDataClass)
    }


}