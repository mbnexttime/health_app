package com.example.psyhealthapp.user.statistics.tests

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class Summary : Fragment(R.layout.stat_tests_summary) {
    private lateinit var chart: RadarChart

    companion object {
        enum class ChartConstants(val value: Float) {
            AXIS_MAXIMUM(80F),
            AXIS_MINIMUM(0F),
            FORM_LINE_WIDTH(10F)
        }

        fun newInstance(values: List<Pair<String, Float>>): Summary {
            val arguments = Bundle()
            val summary = Summary()
            arguments.putFloatArray("values", values.map { it.second }.toFloatArray())
            arguments.putStringArray("names", values.map { it.first }.toTypedArray())
            summary.arguments = arguments
            return summary
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = view.findViewById(R.id.chart)
        arguments?.let {
            val names = it.getStringArray("names")?.toList()
            val values = it.getFloatArray("values")?.toList()
            names?.let { values?.let { setupChart(names, values) } }
        }
    }

    private fun setupChart(names: List<String>, values: List<Float>) {
        val formatTags = names.mapIndexed { it, i -> "$i,\n${values[it].toInt()}" }
        val data = values.map { it -> RadarEntry(it) }

        val set = RadarDataSet(data, "chart")

        set.apply {
            color = ContextCompat.getColor(
                requireActivity(),
                R.color.stat_tests_tapping_chart_lineColor_1
            )
            fillColor =
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.stat_tests_tapping_chart_lineColor_2
                )
            formLineWidth = ChartConstants.FORM_LINE_WIDTH.value
            setDrawValues(false)
            highlightCircleFillColor =
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.stat_tests_tapping_chart_lineColor_2
                )
            setDrawFilled(true)
        }

        chart.yAxis.apply {
            setDrawLabels(false)
            setDrawAxisLine(false)
            this.axisMaximum = ChartConstants.AXIS_MAXIMUM.value
            this.axisMinimum = ChartConstants.AXIS_MINIMUM.value
        }

        chart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(formatTags)
            typeface = ResourcesCompat.getFont(requireActivity(), R.font.oswald)
        }

        chart.apply {
            setData(RadarData(set))
            description.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(context, R.color.stat_cardBackground))
            legend.isEnabled = false
            webColor =
                ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_3)
            webColorInner =
                ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_2)
            animateX(250)
            invalidate()
        }
    }
}