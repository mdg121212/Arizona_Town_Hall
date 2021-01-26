package com.mattg.aztownhall.ui.social2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mattg.aztownhall.databinding.IWillPhotoItemBinding


class IWillAdapter(val context: Context, val photos: List<IWillPhotoItem>) :
    RecyclerView.Adapter<IWillAdapter.IWillViewHolder>() {

    class IWillViewHolder private constructor(private val binding: IWillPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): IWillViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IWillPhotoItemBinding.inflate(layoutInflater, parent, false)
                return IWillViewHolder(binding)

            }
        }

        fun bind(item: IWillPhotoItem) {
            binding.item = item
            Glide.with(binding.ivPhotoitemImage)
                .load(item.image)
                .into(binding.ivPhotoitemImage)
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IWillViewHolder {
        return IWillViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: IWillViewHolder, position: Int) {
        val item = photos[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

}