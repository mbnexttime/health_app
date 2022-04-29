package com.example.psyhealthapp.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException

class RecyclerViewAdapterWithDelegates(
    var delegates: List<RecyclerViewAdapterDelegate<*, *>>,
    var items: List<RecyclerViewItem>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegate = delegates[getItemViewType(position)]
        delegate as RecyclerViewAdapterDelegate<RecyclerView.ViewHolder, RecyclerViewItem>
        delegate.onBindViewHolder(items[position], holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        delegates.withIndex().forEach {
            if (it.value.matches(items[position])) {
                return it.index
            }
        }
        throw RuntimeException()
    }
}