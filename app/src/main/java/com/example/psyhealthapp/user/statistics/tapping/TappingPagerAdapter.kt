package com.example.psyhealthapp.user.statistics.tapping

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.psyhealthapp.user.testing.results.TappingTestResult

class TappingPagerAdapter(
    fragmentManager: FragmentManager,
    val tappingTestResult: TappingTestResult
) :
    FragmentStatePagerAdapter(fragmentManager) {
    private val pages = listOf("левая рука", "правая рука")

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TappingSubFragment.getInstance(tappingTestResult.leftHandMoments)
            else -> TappingSubFragment.getInstance(tappingTestResult.rightHandMoments)
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return pages[position]
    }
}