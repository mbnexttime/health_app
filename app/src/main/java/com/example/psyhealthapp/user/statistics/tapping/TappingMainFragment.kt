package com.example.psyhealthapp.user.statistics.tapping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.StatTappingBinding
import com.example.psyhealthapp.util.DynamicHeightViewPager
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import by.kirich1409.viewbindingdelegate.viewBinding
import java.util.*

class TappingMainFragment : Fragment(R.layout.stat_tapping) {
    private val viewBinding by viewBinding(StatTappingBinding::bind)
    private lateinit var tappingCollectionPagerAdapter: TappingPagerAdapter

    @SuppressLint("SetTextI18n")
    private fun setupTextViews(results: TappingTestResult.ClearlyResults) {
        viewBinding.asymmetryRatio.text = String.format(Locale.US, fmt, results.asymmetry)
        viewBinding.nervousSystemPower.text =
            "${String.format(Locale.US, fmt, results.nervousSystemPowerRatio)}%"
        viewBinding.typeOfNervousSystem.text = results.nervousSystemType
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val result = it.getParcelable<TappingTestResult>(TEST_RESULT)
            result?.let {
                tappingCollectionPagerAdapter = TappingPagerAdapter(
                    childFragmentManager,
                    result
                )
                viewBinding.tappingPager.adapter = tappingCollectionPagerAdapter
                setupTextViews(result.getClearlyResult())
            }
        }
    }

    companion object {
        private const val fmt = "%.2f"
        private const val TEST_RESULT = "testResult"

        fun newInstance(
            tappingResult: TappingTestResult?
        ): TappingMainFragment {
            val arguments = Bundle()
            val tapping = TappingMainFragment()
            arguments.putParcelable(TEST_RESULT, tappingResult)
            tapping.arguments = arguments
            return tapping
        }
    }
}
