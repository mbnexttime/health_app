package com.example.psyhealthapp.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface RecyclerViewAdapterDelegate<VH: RecyclerView.ViewHolder, I: RecyclerViewItem> {
    fun onCreateViewHolder(parent: ViewGroup): VH 
    
    fun onBindViewHolder(item: I, vh: VH)
    
    fun matches(item: RecyclerViewItem): Boolean
}