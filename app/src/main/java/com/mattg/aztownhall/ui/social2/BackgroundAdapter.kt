package com.mattg.aztownhall.ui.social2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mattg.aztownhall.databinding.GradientItemBinding


class BackgroundAdapter(
    val context: Context,
    val backgrounds: List<BackgroundItem>,
    private val clicklistener: BackRoundClickListener
) :
    RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder>() {
    class BackgroundViewHolder private constructor(val binding: GradientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): BackgroundViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GradientItemBinding.inflate(layoutInflater, parent, false)
                return BackgroundViewHolder(binding)
            }
        }
        fun bind(item: BackgroundItem, clicklistener: BackRoundClickListener, context: Context){
            binding.item = item
            Glide.with(context)
                .load(item.backgroundImage)
                .into(binding.ivGradientItem)
            binding.root.setOnClickListener {
                clicklistener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackgroundViewHolder {
        return BackgroundViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BackgroundViewHolder, position: Int) {
        val background = backgrounds[position]
        holder.bind(background, clicklistener, context)
    }

    override fun getItemCount(): Int {
        return backgrounds.size
    }
}