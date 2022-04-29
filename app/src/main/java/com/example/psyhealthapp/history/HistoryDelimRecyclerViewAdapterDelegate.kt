package com.example.psyhealthapp.history

import android.view.View
import android.view.ViewGroup
import com.example.psyhealthapp.R
import com.example.psyhealthapp.util.RecyclerViewAdapterDelegate
import com.example.psyhealthapp.util.RecyclerViewItem

class HistoryDelimRecyclerViewAdapterDelegate :
    RecyclerViewAdapterDelegate<HistoryDelimViewHolder, HistoryDelimRecyclerViewItem> {
    override fun onCreateViewHolder(parent: ViewGroup): HistoryDelimViewHolder {
        val view = View(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            parent.context.resources.getDimensionPixelSize(R.dimen.history_show_vertical_space)
        )
        return HistoryDelimViewHolder(view)
    }

    override fun onBindViewHolder(item: HistoryDelimRecyclerViewItem, vh: HistoryDelimViewHolder) = Unit

    override fun matches(item: RecyclerViewItem): Boolean {
        return item is HistoryDelimRecyclerViewItem
    }
}