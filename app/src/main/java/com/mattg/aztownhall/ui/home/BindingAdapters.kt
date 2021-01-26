package com.mattg.aztownhall.ui.home




import android.widget.TextView
import androidx.databinding.BindingAdapter

import java.util.*



@BindingAdapter("android:loadIWillText")
fun loadText(textView: TextView, string: String){
    val stringToLoad: String = if (string.toLowerCase(Locale.ROOT).startsWith("i will")){
        string.capitalize(Locale.ROOT)
    } else {
        "I will $string"
    }

    textView.text = stringToLoad
}

@BindingAdapter("android:setUserText")
fun setUserText(textView: TextView, input: Int){
    textView.text = input.toString()
}