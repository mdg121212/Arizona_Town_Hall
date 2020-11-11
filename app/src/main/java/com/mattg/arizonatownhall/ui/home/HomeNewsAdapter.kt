package com.mattg.arizonatownhall.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mattg.arizonatownhall.utils.NewsClickListener
import com.mattg.arizonatownhall.databinding.NewsItemBinding

class HomeNewsAdapter(val context: Context, private val news: List<NewsItem>, private val clickListener: NewsClickListener) :
    RecyclerView.Adapter<HomeNewsAdapter.NewsItemViewHolder>() {

    class NewsItemViewHolder private constructor(private val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): NewsItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
                return NewsItemViewHolder(binding)
            }

        }
        fun bind(newsItem: NewsItem, clickListener: NewsClickListener){
        binding.newsItem = newsItem
            binding.root.setOnClickListener {
                clickListener.onClick(newsItem, 0, newsItem.url!!)

            }
            binding.root.setOnLongClickListener {
                clickListener.onLongClick(newsItem, 1, newsItem.url!!)
                true
            }
            binding.executePendingBindings()
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsItemViewHolder {
        return NewsItemViewHolder.from(parent)
    }


    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
       val newsItem = news[position]
        holder.bind(newsItem, clickListener)

    }


}