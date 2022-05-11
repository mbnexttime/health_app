package com.example.psyhealthapp.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class DynamicHeightViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {
    companion object {
        const val TAB_LAYOUT_HEIGHT = 150
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var localHeightMeasureSpec = heightMeasureSpec

        val height = (0 until childCount).map {
            val child = getChildAt(it)
            child.measure(
                widthMeasureSpec,
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            child.measuredHeight
        }.maxOrNull() ?: 0

        if (height != 0) {
            localHeightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, localHeightMeasureSpec + TAB_LAYOUT_HEIGHT)
    }
}