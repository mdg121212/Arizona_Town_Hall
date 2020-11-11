package com.mattg.arizonatownhall.ui.social2

import com.mattg.arizonatownhall.stickerView.Sticker


class StickerClickListener(val clickListener: (item: Sticker) -> Unit){
    fun onClick(item: Sticker) = clickListener(item)
}