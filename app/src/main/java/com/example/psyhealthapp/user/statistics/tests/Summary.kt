package com.example.psyhealthapp.user.statistics.tests

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.psyhealthapp.R
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class Summary : CardView {
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

    private lateinit var chart: RadarChart
    private val tags =
        listOf("мышление", "контроль", "реакция", "стабильность", "воля", "внимание", "регуляция")
    private val testValues = listOf(90F, 81F, 75F, 66F, 52F, 37F, 67F)
    private val formatTags = tags.mapIndexed { it, i -> "$i,\n${testValues[it].toInt()}" }

    private fun setupChart() {
        chart = findViewById(R.id.chart)

        val data = testValues.map { it -> RadarEntry(it) }
        val set = RadarDataSet(data, "chart")

        set.color = ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_1)
        set.fillColor =
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_2)
        set.formLineWidth = 10F
        set.setDrawValues(false)
        set.highlightCircleFillColor =
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_2)
        set.setDrawFilled(true)

        chart.yAxis.setDrawLabels(false)
        chart.yAxis.setDrawAxisLine(false)
        chart.yAxis.axisMinimum = 0F
        chart.yAxis.axisMaximum = 80F

        chart.setTouchEnabled(false)
        chart.data = RadarData(set)
        chart.description.isEnabled = false
        chart.setBackgroundColor(ContextCompat.getColor(context, R.color.stat_cardBackground))
        chart.legend.isEnabled = false
        chart.animateX(250)

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(formatTags)
        chart.xAxis.typeface = ResourcesCompat.getFont(context, R.font.oswald)

        chart.webColor =
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_3)
        chart.webColorInner =
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_2)

        chart.invalidate()
    }

    private fun setupView(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.stat_tests_summary, this)
        setupChart()
    }
}