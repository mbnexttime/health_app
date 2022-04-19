package com.example.psyhealthapp.user.statistics.tests

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.psyhealthapp.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class Tapping : CardView {
    private lateinit var chart: LineChart
    private val sampleDataRightHand =
        listOf(Pair(5F, 37F), Pair(10F, 30F), Pair(15F, 28F), Pair(20F, 29F), Pair(25F, 27F))
    private val sampleDataLeftHand =
        listOf(Pair(5F, 40F), Pair(10F, 29F), Pair(15F, 35F), Pair(20F, 24F), Pair(25F, 27F))
    private val sampleMid = 30.6F

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

        fun getDataSet(ds: List<Pair<Float, Float>>, col: Int, setName: String): LineDataSet {
            val dataSet = LineDataSet(ds.map { i -> Entry(i.first, i.second) }, setName)
            dataSet.lineWidth = 2F
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.color = col
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            return dataSet
        }

        val chartData = LineData()

        val ds1 = getDataSet(
            sampleDataRightHand,
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_1),
            "right_hand_set"
        )
        val ds2 = getDataSet(
            sampleDataLeftHand,
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_2),
            "left_hand_set"
        )
        chartData.addDataSet(ds1)
        chartData.addDataSet(ds2)

        val axisLeft = chart.axisLeft
        axisLeft.addLimitLine(LimitLine(sampleMid))
        axisLeft.setDrawGridLines(false)
        axisLeft.setDrawLabels(false)
        axisLeft.axisMinimum = 0F
        axisLeft.axisMaximum = 50F
        axisLeft.axisLineWidth = 1F
        axisLeft.axisLineColor =
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_3)

        val xAxis = chart.xAxis
        xAxis.setDrawLabels(false)
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.axisLineWidth = 1F
        xAxis.axisLineColor = axisLeft.axisLineColor

        chart.data = chartData
        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.setBackgroundColor(ContextCompat.getColor(context, R.color.stat_cardBackground))
        chart.legend.isEnabled = false
        chart.setScaleEnabled(false)
        chart.animateX(250)

        chart.invalidate()
    }

    private fun setupView(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.stat_tests_tapping, this)
        setupChart()
    }
}