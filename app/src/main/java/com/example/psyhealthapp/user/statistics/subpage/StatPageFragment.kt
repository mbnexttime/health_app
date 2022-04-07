package com.example.psyhealthapp.user.statistics.subpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R

class StatPageFragment : Fragment(R.layout.statpage_fragment) {
    companion object {
        fun newInstance() : StatPageFragment {
            return StatPageFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.statpage_fragment, null)
        return view
    }
}
