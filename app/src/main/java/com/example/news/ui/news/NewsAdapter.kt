package com.example.news.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.api.model.newsResponse.News
import com.example.news.databinding.ItemNewsBinding

class NewsAdapter(var newsList: List<News?>? = null) :
    RecyclerView.Adapter<NewsAdapter.viewHolder>() {

    class viewHolder(val itemBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(news: News?) {
            itemBinding.news = news
            itemBinding.invalidateAll() // to make image bind quickly
        }
    }

    override fun getItemCount(): Int = newsList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemBinding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return viewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val news = newsList!![position]
        holder.bind(news)
        if (onItemClickListener != null) {
            holder.itemBinding.root.setOnClickListener {
                onItemClickListener?.onCLickItem(news)
            }
        }
    }

    fun bindNews(articles: List<News?>?) {
        newsList = articles
        notifyDataSetChanged()
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onCLickItem(news: News?)
    }
}