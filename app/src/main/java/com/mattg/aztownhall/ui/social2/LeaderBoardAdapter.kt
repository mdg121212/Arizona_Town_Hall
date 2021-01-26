package com.mattg.aztownhall.ui.social2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mattg.aztownhall.databinding.LeaderboardItemBinding
import com.mattg.aztownhall.utils.User

class LeaderBoardAdapter(val context: Context, private val leaders: List<User>) :
    RecyclerView.Adapter<LeaderBoardAdapter.LeaderItemViewHolder>() {

    class LeaderItemViewHolder(val binding: LeaderboardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): LeaderItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LeaderboardItemBinding.inflate(layoutInflater, parent, false)
                return LeaderItemViewHolder(binding)
            }
        }

        fun bind(user: User) {
            binding.user = user
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderItemViewHolder {
        return LeaderItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LeaderItemViewHolder, position: Int) {
        val user = leaders[position]
        holder.bind(user)
    }


    override fun getItemCount(): Int {
        return leaders.size
    }


}