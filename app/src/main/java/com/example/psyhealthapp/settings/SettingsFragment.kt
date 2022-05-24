package com.example.psyhealthapp.settings

import android.os.Bundle
import android.view.View
import android.widget.*
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R


class SettingsFragment : Fragment(R.layout.settings_fragment) {

    lateinit var llBackground: LinearLayout
    private lateinit var sbColor: SeekBar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        llBackground = view.findViewById(R.id.llBackground)
        sbColor = view.findViewById(R.id.sbColor)
        sbColor.setOnSeekBarChangeListener(seekBarChangeListener)
    }

    private val minR = 180
    private val minG = 180
    private val minB = 180
    private val maxR = 252
    private val maxG = 252
    private val maxB = 252

    private var r: Int = minR
    private var g: Int = minG
    private var b: Int = minB

    private val seekBarChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            when {
                progress < 25 -> {
                    r = minR + progress * 3
                }
                progress < 25 * 2 -> {
                    r = maxR
                    g = minG + (progress % 25) * 3
                }
                progress < 25 * 3 -> {
                    g = maxG
                    r = maxR - (progress % 25) * 3
                }
                progress < 25 * 4 -> {
                    g = maxG
                    b = minB + (progress % 25) * 3
                }
                progress < 25 * 5 -> {
                    b = maxB
                    g = maxG - (progress % 25) * 3
                }
                progress < 25 * 6 -> {
                    b = maxB
                    r = minR + (progress % 25) * 3
                }
                progress < 25 * 7 -> {
                    r = maxR
                    b = maxB - (progress % 25) * 3
                }
                else -> {
                    r = maxR
                    g = maxG
                    b = maxB
                }
            }

            llBackground.setBackgroundColor(Color.argb(255, r, g, b))

        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }



}

