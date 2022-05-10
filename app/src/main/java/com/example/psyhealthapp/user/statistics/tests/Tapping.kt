package com.example.psyhealthapp.user.statistics.tests

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.statistics.util.Calcus
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*

class Tapping : Fragment(R.layout.stat_tests_tapping) {
    private lateinit var chart: LineChart

    companion object {
        enum class ChartConstants(val value: Float) {
            LINE_WIDTH(2F),
            AXIS_MINIMUM(0F),
            AXIS_MAXIMUM(50F),
        }

        fun newInstance(
            tappingResult: TappingTestResult?
        ): Tapping {
            val arguments = Bundle()
            val tapping = Tapping()
            arguments.putParcelable("testResult", tappingResult)
            tapping.arguments = arguments
            return tapping
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = view.findViewById(R.id.chart)

        arguments?.let {
            val result = it.getParcelable<TappingTestResult>("testResult")
            result?.let {
                setupChart(result)
            }
        }
    }

    private fun getDataSet(
        pointsAndValues: List<Pair<Float, Float>>,
        col: Int,
        setName: String
    ): LineDataSet {
        println(pointsAndValues)
        val dataSet = LineDataSet(pointsAndValues.map { Entry(it.first, it.second) }, setName)
        dataSet.apply {
            lineWidth = ChartConstants.LINE_WIDTH.value
            setDrawCircles(false)
            setDrawValues(false)
            color = col
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }
        return dataSet
    }

    private fun setupChart(result: TappingTestResult) {
        val chartData = LineData()

        val ds1 = getDataSet(
            Calcus.getDerivate(result.rightHandMoments.mapIndexed { it, i ->
                Pair(
                    i,
                    it.toFloat()
                )
            }.toMutableList()),
            ContextCompat.getColor(
                requireActivity(), R.color.stat_tests_tapping_chart_lineColor_1
            ),
            "right_hand_set"
        )

        val ds2 = getDataSet(
            Calcus.getDerivate(result.leftHandMoments.mapIndexed { it, i ->
                Pair(
                    i,
                    it.toFloat()
                )
            }.toMutableList()),
            ContextCompat.getColor(
                requireActivity(), R.color.stat_tests_tapping_chart_lineColor_2
            ),
            "left_hand_set"
        )

        chartData.addDataSet(ds1)
        chartData.addDataSet(ds2)

        chart.axisLeft.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
//            axisMinimum = 0F
//            axisMaximum = 20F
            axisLineWidth = 1F
            axisLineColor =
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.stat_tests_tapping_chart_lineColor_3
                )
        }

        chart.axisRight.apply {
            setDrawLabels(false)
            setDrawGridLines(false)
            axisMinimum = 0F
            axisMaximum = 10F
            axisLineWidth = 1F
            axisLineColor =
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.stat_tests_tapping_chart_lineColor_3
                )
        }

        chart.xAxis.apply {
            setDrawLabels(false)
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM_INSIDE
            axisLineWidth = 1F
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