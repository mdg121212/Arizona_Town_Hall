package com.mattg.aztownhall.stickerView


import android.graphics.Bitmap
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable


data class Sticker(var drawable: Drawable, val resourceId: Int){
    val image = drawable
    val id = resourceId
    var xml: Drawable? = null
    var bitmap: Bitmap? = null
    var colorFilter: Int? = null

    constructor(drawable: Drawable, resourceId: Int, xmlImage: Drawable) : this(drawable, resourceId) {
        xml = xmlImage
    }

    fun setColorFilter(filter: ColorFilter){
        image.colorFilter = filter

    }
    fun setNewBitmap(bm: Bitmap){
        bitmap = bm
    }
}