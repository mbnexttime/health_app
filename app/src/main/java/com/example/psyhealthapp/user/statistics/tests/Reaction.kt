package com.example.psyhealthapp.user.statistics.tests

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.example.psyhealthapp.R

class Reaction : CardView {
    constructor(context: Context) : super(context) {
        setupView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setupView(context)
    }

    private fun setupView(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.stat_tests_reaction, this)
    }
}