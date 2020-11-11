package com.mattg.arizonatownhall.ui.social2



class BackRoundClickListener(val clickListener: (item: BackgroundItem) -> Unit) {
    fun onClick(item: BackgroundItem) = clickListener(item)
}