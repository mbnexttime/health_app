package com.example.psyhealthapp.settings

import android.os.Bundle
import android.view.View
import android.widget.*
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.SettingsFragmentBinding
import by.kirich1409.viewbindingdelegate.viewBinding


class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private lateinit var sbColor: SeekBar
    private val viewBinding by viewBinding(SettingsFragmentBinding::bind)

    private data class Colour(var white : Float, var black : Float,
                              var progressColour : Int)

    private val primaryColour = Colour(0.0F, 0.0F, 0)
    private val secondaryColour = Colour(0.0F, 0.0F, 0)
    private val backgroundColour = Colour(0.0F, 0.0F, 0)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.sbColorPrimary.setOnSeekBarChangeListener(seekBarPrimaryColourChangeListener)
        viewBinding.doubleSeekBarPrimary.viewBinding.sbLeft.setOnSeekBarChangeListener(seekBarPrimaryBlackChangeListener)
        viewBinding.doubleSeekBarPrimary.viewBinding.sbRight.setOnSeekBarChangeListener(seekBarPrimaryWhiteChangeListener)

        viewBinding.sbColorSecondary.setOnSeekBarChangeListener(seekBarSecondaryColourChangeListener)
        viewBinding.doubleSeekBarSecondary.viewBinding.sbLeft.setOnSeekBarChangeListener(seekBarSecondaryBlackChangeListener)
        viewBinding.doubleSeekBarSecondary.viewBinding.sbRight.setOnSeekBarChangeListener(seekBarSecondaryWhiteChangeListener)

        viewBinding.sbColorBackground.setOnSeekBarChangeListener(seekBarBackgroundColourChangeListener)
        viewBinding.doubleSeekBarBackground.viewBinding.sbLeft.setOnSeekBarChangeListener(seekBarBackgroundBlackChangeListener)
        viewBinding.doubleSeekBarBackground.viewBinding.sbRight.setOnSeekBarChangeListener(seekBarBackgroundWhiteChangeListener)

    }

    private val seekBarPrimaryColourChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            primaryColour.progressColour = progress
            val (r, g, b) = controlChange(primaryColour)
            viewBinding.primaryText.setTextColor(Color.argb(255, r, g, b))
            viewBinding.backgroundText.setTextColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarPrimaryWhiteChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            primaryColour.white = progress.toFloat() / 100
            val (r, g, b) = controlChange(primaryColour)
            viewBinding.primaryText.setTextColor(Color.argb(255, r, g, b))
            viewBinding.backgroundText.setTextColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarPrimaryBlackChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            primaryColour.black = progress.toFloat() / 100
            val (r, g, b) = controlChange(primaryColour)
            viewBinding.primaryText.setTextColor(Color.argb(255, r, g, b))
            viewBinding.backgroundText.setTextColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }



    private val seekBarSecondaryColourChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            secondaryColour.progressColour = progress
            val (r, g, b) = controlChange(secondaryColour)
            viewBinding.secondaryText.setTextColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarSecondaryWhiteChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            secondaryColour.white = progress.toFloat() / 100
            val (r, g, b) = controlChange(secondaryColour)
            viewBinding.secondaryText.setTextColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarSecondaryBlackChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            secondaryColour.black = progress.toFloat() / 100
            val (r, g, b) = controlChange(secondaryColour)
            viewBinding.secondaryText.setTextColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }



    private val seekBarBackgroundColourChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            backgroundColour.progressColour = progress
            val (r, g, b) = controlChange(backgroundColour)
            viewBinding.llBackground.setBackgroundColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarBackgroundWhiteChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            backgroundColour.white = progress.toFloat() / 100
            val (r, g, b) = controlChange(backgroundColour)
            viewBinding.llBackground.setBackgroundColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarBackgroundBlackChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            backgroundColour.black = progress.toFloat() / 100
            val (r, g, b) = controlChange(backgroundColour)
            viewBinding.llBackground.setBackgroundColor(Color.argb(255, r, g, b))
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private fun controlChange(colour : Colour): Triple<Int, Int, Int> {
        var r : Int
        var g : Int
        var b : Int
        val progress = colour.progressColour
        when {
            progress < 256 -> {
                r = 255
                g = progress % 256
                b = 0
            }
            progress < 256 * 2 -> {
                r = 255 - progress % 256
                g = 255
                b = 0
            }
            progress < 256 * 3 -> {
                r = 0
                g = 255
                b = progress % 256
            }
            progress < 256 * 4 -> {
                r = 0
                g = 256 - progress % 256
                b = 255
            }
            progress < 256 * 5 -> {
                r = progress % 256
                g = 0
                b = 255
            }
            else -> {
                r = 255
                g = 0
                b = 256 - progress % 256
            }
        }

        r += ((255 - r) * colour.white).toInt()
        r -= (r * colour.black).toInt()
        g += ((255 - g) * colour.white).toInt()
        g -= (g * colour.black).toInt()
        b += ((255 - b) * colour.white).toInt()
        b -= (b * colour.black).toInt()

        return Triple(r, g, b)
    }
}

