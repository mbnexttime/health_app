package com.example.psyhealthapp.history

import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psyhealthapp.R
import org.w3c.dom.Text

class HistoryViewHolder(item: LinearLayout): RecyclerView.ViewHolder(item) {
    val date = item.findViewById<TextView>(R.id.history_date)
    val text = item.findViewById<TextView>(R.id.history_text)
}