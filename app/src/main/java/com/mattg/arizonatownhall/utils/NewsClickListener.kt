package com.mattg.arizonatownhall.utils

import com.mattg.arizonatownhall.ui.home.NewsItem
import io.swagger.client.models.Event

class NewsClickListener(val clickListener: (item: NewsItem, press: Int, url: String) -> Unit) {
    fun onLongClick(item: NewsItem, press: Int, url: String) = clickListener(item, press, url)
    fun onClick(item: NewsItem, press: Int, url: String) = clickListener(item, press, url)
}