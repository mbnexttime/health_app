package com.example.psyhealthapp.user.statistics.tests

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.psyhealthapp.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class Reaction : CardView {
    private lateinit var chart: LineChart
    private val days = listOf("29.04", "вчера", "вчера", "сегодня", "сегодня")
    private val values = listOf(405F, 458F, 300F, 357F, 380F)

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

    private fun setupChart() {
        chart = findViewById(R.id.chart)

        val dataSet =
            LineDataSet(values.mapIndexed { it, i -> Entry(it.toFloat(), i) }, "reaction_attempts")
        dataSet.lineWidth = 2F
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(true)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.valueTextSize = 14F
        dataSet.color =
            ContextCompat.getColor(context, R.color.stat_tests_lastDaysActivity_barColor_3)
        dataSet.setDrawFilled(true)
        dataSet.fillColor = ContextCompat.getColor(context, R.color.stat_tests_tapping_textColor_1)
        dataSet.valueTextColor =
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_3)
        dataSet.valueTypeface = ResourcesCompat.getFont(context, R.font.oswald)
        dataSet.enableDashedLine(10F, 10F, 10F)

        chart.setTouchEnabled(false)
        chart.description.isEnabled = false
        chart.data = LineData(dataSet)
        chart.xAxis.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
        chart.invalidate()
    }

    private fun setupView(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.stat_tests_reaction, this)
        setupChart()
    }
}