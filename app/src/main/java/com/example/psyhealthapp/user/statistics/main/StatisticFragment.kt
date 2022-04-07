package com.example.psyhealthapp.user.statistics.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.statistics.subpage.StatPageFragment

class StatisticFragment : Fragment(R.layout.statistic_fragment) {
    private val navButtons = mutableListOf<Button>()

    companion object {
        const val PAGE_COUNT = 3
    }
    private lateinit var pager : ViewPager
    private lateinit var pagerAdapter: PagerAdapter

    private fun initNavButtons(view : View) {
        navButtons.add(view.findViewById(R.id.button7))
        navButtons.add(view.findViewById(R.id.button8))
        navButtons.add(view.findViewById(R.id.button9))
        navButtons.map {
            it.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.inactive_stat_page, null))
            it.isEnabled = false
            it.setTextColor(Color.BLACK)
        }
        navButtons[0].setBackgroundColor(ResourcesCompat.getColor(resources, R.color.active_stat_page, null))
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.statistic_fragment, null)
        initNavButtons(view)

        pagerAdapter = MyFragmentPagerAdapter(childFragmentManager)
        pager = view.findViewById<ViewPager>(R.id.pager)

        pager.adapter = pagerAdapter

        pager.setOnPageChangeListener(object : OnPageChangeListener {
            var current_page = 0
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                navButtons[current_page].setBackgroundColor(ResourcesCompat.getColor(resources, R.color.inactive_stat_page, null))
                current_page = position
                navButtons[current_page].setBackgroundColor(ResourcesCompat.getColor(resources, R.color.active_stat_page, null))
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        return view
    }

    class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getItem(position: Int): Fragment {
            return StatPageFragment.newInstance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
