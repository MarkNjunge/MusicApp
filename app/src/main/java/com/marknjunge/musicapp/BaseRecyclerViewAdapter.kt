package com.marknjunge.musicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewAdapter<T>(
    @LayoutRes
    private val layoutRes: Int,
    private val bind: View.(item: T) -> Unit
) : RecyclerView.Adapter<BaseRecyclerViewAdapter<T>.ViewHolder>() {
    private val items by lazy { mutableListOf<T>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(layoutRes))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(newItems: List<T>, animated: Boolean = true) {
        if (animated) {
            val result = DiffUtil.calculateDiff(ItemDiffCallback(items, newItems.toMutableList()))

            items.clear()
            items.addAll(newItems)
            result.dispatchUpdatesTo(this)
        } else {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: T) = bind(itemView, item)
    }

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }
}