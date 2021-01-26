package com.mattg.aztownhall.ui.social2

import com.mattg.aztownhall.stickerView.Sticker


class StickerClickListener(val clickListener: (item: Sticker) -> Unit){
    fun onClick(item: Sticker) = clickListener(item)
}