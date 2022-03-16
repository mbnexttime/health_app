package com.example.psyhealthapp.debug

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.psyhealthapp.R

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    companion object {
        const val PAGE_COUNT = 10
    }

    private lateinit var pager : ViewPager
    lateinit var pagerAdapter: PagerAdapter

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, null)

        pagerAdapter = MyFragmentPagerAdapter(parentFragmentManager)
        pager = view.findViewById<ViewPager>(R.id.pager)

        pager.adapter = pagerAdapter

        pager.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })

        return view
    }

    class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getItem(position: Int): Fragment {
            return StatFragment.newInstance(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}