package com.mattg.aztownhall.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mattg.aztownhall.databinding.RvHomeUpcomingeventsItemBinding
import com.mattg.aztownhall.utils.ClickListener
import io.swagger.client.models.Event


class HomeEventsAdapter(val context: Context, private val events: List<Event>, private val clickListener: ClickListener,
                       ) :
RecyclerView.Adapter<HomeEventsAdapter.ViewItemViewHolder>() {


    class ViewItemViewHolder private constructor(private val binding: RvHomeUpcomingeventsItemBinding):
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ViewItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RvHomeUpcomingeventsItemBinding.inflate(layoutInflater, parent, false)
            return ViewItemViewHolder(binding)
        }

    }

    fun bind(event: Event, clickListener: ClickListener) {
        binding.event = event
        binding.root.setOnClickListener {
            clickListener.onClick(event, 0, event.Id)
        }
        binding.root.setOnLongClickListener {
            clickListener.onLongClick(event, 1, event.Id)
            true
        }
        binding.executePendingBindings()
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemViewHolder {
        return ViewItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewItemViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event, clickListener)
    }

    override fun getItemCount(): Int {
        return events.size
    }
}