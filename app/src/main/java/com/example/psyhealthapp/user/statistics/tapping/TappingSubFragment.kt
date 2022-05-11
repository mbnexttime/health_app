package com.example.psyhealthapp.user.statistics.tapping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class TappingSubFragment : Fragment(R.layout.stat_tapping_subfragment) {
    private lateinit var chart: LineChart

    companion object {
        const val LINE_WIDTH = 2F
        const val AXIS_LINE_WIDTH = 1F
        const val YAXIS_MAXIMUM_COEF = 1.2F
        const val YAXIS_MINIMUM_COEF = 0.7F
        const val DASHED_LINE_LENGTH = 10F

        fun getInstance(moments: List<Float>): TappingSubFragment {
            val arguments = Bundle()
            val tappingSubFragment = TappingSubFragment()
            arguments.putFloatArray("moments", moments.toFloatArray())
            tappingSubFragment.arguments = arguments
            return tappingSubFragment
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart = view.findViewById(R.id.chart)
        arguments?.let {
            val result = it.getFloatArray("moments")?.toList()
            result?.let {
                setupChart(result.toList())
            }
        }
    }

    private fun setupChart(result: List<Float>) {
        val chartData = LineData()

        val graphData = TappingTestResult.getGraph(result)
        val dataSet =
            LineDataSet(
                graphData.map { Entry(it.first, it.second) },
                "label"
            )

        dataSet.apply {
            lineWidth = LINE_WIDTH
            color = ContextCompat.getColor(
                requireActivity(), R.color.stat_tests_tapping_chart_lineColor_1
            )
            setDrawCircles(false)
            setDrawValues(false)
            enableDashedLine(
                DASHED_LINE_LENGTH,
                DASHED_LINE_LENGTH,
                DASHED_LINE_LENGTH
            )
        }

        chartData.addDataSet(dataSet)

        val maxY = graphData.maxByOrNull { it.second }!!.second
        val minY = graphData.minByOrNull { it.second }!!.second
        val average = graphData.map { it.second }.average().toFloat()

        chart.axisLeft.apply {
            setDrawGridLines(false)

            addLimitLine(LimitLine(average))
            addLimitLine(LimitLine(minY))

            limitLines[1].lineWidth = 0F
            limitLines[1].lineColor = dataSet.color
            limitLines[1].label = " нажатий всего: ${result.size}"
            limitLines[1].labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM

            axisMinimum = YAXIS_MINIMUM_COEF * minY
            axisMaximum = YAXIS_MAXIMUM_COEF * maxY

            axisLineWidth = AXIS_LINE_WIDTH
            axisLineColor = ContextCompat.getColor(
                requireActivity(),
                R.color.stat_tests_tapping_chart_lineColor_3
            )

            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }

        chart.axisRight.apply {
            setDrawLabels(false)
            setDrawGridLines(false)

            axisMinimum = 0F
            axisMaximum = YAXIS_MAXIMUM_COEF * maxY

            axisLineWidth = AXIS_LINE_WIDTH
            axisLineColor = ContextCompat.getColor(
                requireActivity(),
                R.color.stat_tests_tapping_chart_lineColor_3
            )
        }

        chart.xAxis.apply {
            setDrawLabels(false)
            setDrawGridLines(false)

            position = XAxis.XAxisPosition.BOTTOM_INSIDE
            axisLineWidth = AXIS_LINE_WIDTH
            axisLineColor = chart.axisLeft.axisLineColor
        }

        chart.apply {
            setTouchEnabled(false)
            data = chartData
            description.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(context, R.color.stat_cardBackground))
            legend.isEnabled = false
            animateX(250)
            invalidate()
        }
    }
}