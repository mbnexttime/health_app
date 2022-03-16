package com.example.psyhealthapp.debug

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R

class StatFragment : Fragment(R.layout.stat_fragment) {
    var pageNumber = 0

    companion object {
        val ARGUMENT_PAGE_NUMBER = "arg_page_number"
        fun newInstance(page : Int) : StatFragment {
            val statFragment = StatFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            statFragment.arguments = arguments
            return statFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments?.getInt(ARGUMENT_PAGE_NUMBER) ?: 0
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.stat_fragment, null)
        val tvPage = view.findViewById<TextView>(R.id.tvPage)
        tvPage.text = "Page " + pageNumber
        return view
    }
}