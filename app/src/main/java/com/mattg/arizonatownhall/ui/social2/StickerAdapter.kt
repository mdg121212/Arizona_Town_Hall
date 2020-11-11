package com.mattg.arizonatownhall.ui.social2

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mattg.arizonatownhall.databinding.StickerItemBinding


import com.mattg.arizonatownhall.stickerView.Sticker
import com.mattg.arizonatownhall.stickerView.StickerInt
import kotlinx.android.synthetic.main.sticker_item.view.*
//List<Sticker>
class StickerAdapter(val context: Context, private val stickers: List<Sticker>, private val clickListener: StickerClickListener) : RecyclerView.Adapter<StickerAdapter.StickerViewHolder>(){

    class StickerViewHolder(val binding: StickerItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): StickerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StickerItemBinding.inflate(layoutInflater, parent, false)
                return StickerViewHolder(binding)
            }
        }

        fun bind(sticker: Sticker, clickListener: StickerClickListener){
           binding.sticker= sticker
            binding.root.setOnClickListener {
                clickListener.onClick(sticker)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
        return StickerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StickerViewHolder, position: Int) {
        val sticker = stickers[position]
        holder.bind(sticker, clickListener)
    }

    override fun getItemCount(): Int {
        return stickers.size
    }

}