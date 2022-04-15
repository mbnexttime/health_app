package com.example.psyhealthapp.onboarding

import android.view.View
import android.widget.EditText
import android.widget.TextView

data class DataViewHolder(
    val edit: EditText,
    val title: TextView,
    val check: (String) -> Boolean,
)