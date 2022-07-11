package com.example.psyhealthapp.history

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.ColorHolder
import com.example.psyhealthapp.util.RecyclerViewAdapterDelegate
import com.example.psyhealthapp.util.RecyclerViewItem
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Inject

class HistoryRecyclerViewAdapterDelegate (
        context: Context?
):
    RecyclerViewAdapterDelegate<HistoryViewHolder, HistoryRecyclerViewItem> {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface MyEntryPoint {
        fun getColorHolder() : ColorHolder
    }

    var colorHolder: ColorHolder?

    init {
        val myEntryPoint = context?.let {
            EntryPointAccessors.fromApplication(it, MyEntryPoint::class.java)
        }
        colorHolder = myEntryPoint?.getColorHolder()
    }



    override fun onCreateViewHolder(parent: ViewGroup): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_view, parent, false) as LinearLayout
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(item: HistoryRecyclerViewItem, vh: HistoryViewHolder) {
        vh.text.text = item.history.text
        val myDate = Date(item.history.date)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("UTC")
        calendar.time = myDate
        val time: Date = calendar.time
        val outputFmt = SimpleDateFormat("MMM dd, yyy h:mm a", Locale.UK)
        val dateAsString = outputFmt.format(time)
        vh.date.text = dateAsString
        val colors = colorHolder?.getColors()
        colors?.let {
            vh.text.setTextColor(it.primary)
            vh.text.setBackgroundColor(it.secondary)
            vh.date.setTextColor(it.secondary)
        }
    }

    override fun matches(item: RecyclerViewItem): Boolean {
        return item is HistoryRecyclerViewItem
    }
}