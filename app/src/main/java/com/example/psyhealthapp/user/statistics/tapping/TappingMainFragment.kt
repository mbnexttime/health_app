package com.example.psyhealthapp.user.statistics.tapping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.util.DynamicHeightViewPager
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import java.text.DecimalFormat
import java.text.Format

class TappingMainFragment : Fragment(R.layout.stat_tapping) {
    private lateinit var tappingPager: DynamicHeightViewPager
    private lateinit var tappingCollectionPagerAdapter: TappingPagerAdapter

    private lateinit var asymmetryTextView: TextView
    private lateinit var nervousSystemPowerTextView: TextView
    private lateinit var nervousSystemTypeTextView: TextView

    @SuppressLint("SetTextI18n")
    private fun setupTextViews(results: TappingTestResult.ClearlyResults) {
        asymmetryTextView.text = String.format(fmt, results.asymmetry)
        nervousSystemPowerTextView.text = "${String.format(fmt, results.nervousSystemPowerRatio)}%"
        nervousSystemTypeTextView.text = results.nervousSystemType
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tappingPager = view.findViewById(R.id.tapping_pager)
        asymmetryTextView = view.findViewById(R.id.asymmetry_ratio)
        nervousSystemPowerTextView = view.findViewById(R.id.nervous_system_power)
        nervousSystemTypeTextView = view.findViewById(R.id.type_of_nervous_system)

        arguments?.let {
            val result = it.getParcelable<TappingTestResult>("testResult")
            result?.let {
                tappingCollectionPagerAdapter = TappingPagerAdapter(
                    childFragmentManager,
                    result
                )
                tappingPager.adapter = tappingCollectionPagerAdapter
                setupTextViews(result.getClearlyResult())
            }
        }
    }

    companion object {
        private const val fmt = "%.2f"

        fun newInstance(
            tappingResult: TappingTestResult?
        ): TappingMainFragment {
            val arguments = Bundle()
            val tapping = TappingMainFragment()
            arguments.putParcelable("testResult", tappingResult)
            tapping.arguments = arguments
            return tapping
        }
    }
}
