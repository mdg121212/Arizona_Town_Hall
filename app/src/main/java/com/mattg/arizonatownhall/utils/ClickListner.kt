package com.mattg.arizonatownhall.utils

import io.swagger.client.models.Event

class ClickListener(val clickListener: (itemId: Int, press: Int, url: Int) -> Unit) {

    fun onLongClick(event: Event, press: Int, url: Int) = clickListener(event.Id, press, url)
    fun onClick(event: Event, press: Int,url: Int) = clickListener(event.Id, press, url)

}