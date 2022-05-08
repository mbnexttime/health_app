package com.example.psyhealthapp.user.statistics.tests

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.statistics.util.RoundedVerticalBarChartRenderer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class LastDaysStat(
    val days: List<String>,
    val counts: List<Int>
) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(days)
        parcel.writeIntArray(counts.toIntArray())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LastDaysStat> {
        override fun createFromParcel(parcel: Parcel): LastDaysStat {
            return LastDaysStat(
                parcel.createStringArray()!!.toList(),
                parcel.createIntArray()!!.toList()
            )
        }

        override fun newArray(size: Int): Array<LastDaysStat?> {
            return arrayOfNulls(size)
        }
    }

}

class LastDaysActivity : Fragment(R.layout.stat_tests_lastdaysactivity) {
    private lateinit var chart: BarChart

    companion object {
        enum class ChartConstants(val value: Float) {
            CHART_BAR_RADIUS(50F),
        }

        fun newInstance(lastDaysStat: LastDaysStat): LastDaysActivity {
            val arguments = Bundle()
            val lastDaysActivity = LastDaysActivity()
            arguments.putParcelable("lastDaysStat", lastDaysStat)
            lastDaysActivity.arguments = arguments
            return lastDaysActivity
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = view.findViewById(R.id.chart)
        arguments?.let {
            val lastDaysStat = it.getParcelable<LastDaysStat>("lastDaysStat")
            lastDaysStat?.let {
                setupChart(lastDaysStat)
            }
        }
    }

    private fun setupChart(lastDaysStat: LastDaysStat) {
        val entries =
            lastDaysStat.counts.mapIndexed { i, it -> BarEntry(i.toFloat(), it.toFloat()) }

        val dataSet = BarDataSet(entries, "last_days_activity")

        dataSet.apply {
            colors = listOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_lastDaysActivity_barColor_1
                ),
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_lastDaysActivity_barColor_2
                ),
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_lastDaysActivity_barColor_4
                ),
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_lastDaysActivity_barColor_4
                ),
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_lastDaysActivity_barColor_3
                )
            )

            valueFormatter = object : ValueFormatter() {
                override fun getBarLabel(barEntry: BarEntry?): String {
                    return barEntry?.y?.toInt().toString()
                }
            }

            valueTextSize = 10F;
            barBorderWidth = 1f;
        }

        val roundedVerticalBarChartRenderer =
            RoundedVerticalBarChartRenderer(chart, chart.animator, chart.viewPortHandler)
        roundedVerticalBarChartRenderer.apply {
            setRightRadius(50F)
            setLeftRadius(50F)
        }

        chart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(lastDaysStat.days)
            granularity = 1f
            isGranularityEnabled = true
            setDrawGridLines(false)
            setDrawAxisLine(false)
            position = XAxis.XAxisPosition.BOTTOM
            textColor =
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_tapping_chart_lineColor_3
                )
        }

        chart.apply {
            renderer = roundedVerticalBarChartRenderer
            setTouchEnabled(false)
            data = BarData(dataSet)
            description.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_cardBackground
                )
            )
            legend.isEnabled = false
            setScaleEnabled(false)
            animateY(250)
            invalidate()
        }
    }
}