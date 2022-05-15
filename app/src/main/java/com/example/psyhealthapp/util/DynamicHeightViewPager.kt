package com.example.psyhealthapp.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.max

class DynamicHeightViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {
    companion object {
        const val TAB_LAYOUT_HEIGHT = 150
    }

    var swipeable = true
    var maxHeight = 0

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (swipeable) super.onInterceptTouchEvent(ev) else false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var localHeightMeasureSpec = heightMeasureSpec

        var height = (0 until childCount).map {
            val child = getChildAt(it)
            child.measure(
                widthMeasureSpec,
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            child.measuredHeight
        }.maxOrNull() ?: 0

        height = max(height, maxHeight)
        maxHeight = max(maxHeight, height)

        if (height != 0) {
            localHeightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, localHeightMeasureSpec + TAB_LAYOUT_HEIGHT)
    }
}