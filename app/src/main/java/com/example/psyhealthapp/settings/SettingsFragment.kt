package com.example.psyhealthapp.settings

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.SettingsFragmentBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.core.ColorHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.settings_fragment) {

    @Inject
    lateinit var colorHolder: ColorHolder

    private lateinit var sbColor: SeekBar
    private val viewBinding by viewBinding(SettingsFragmentBinding::bind)

    private var primaryColour = Colour(0.0F, 0.0F, Color.argb(255, 255, 0, 0))
    private var secondaryColour = Colour(0.0F, 0.0F, Color.argb(255, 255, 0, 0))
    private var backgroundColour = Colour(0.0F, 0.0F, Color.argb(255, 255, 0, 0))

    private val alpha = 255


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewsColours()

        viewBinding.sbColorPrimary.setOnSeekBarChangeListener(seekBarPrimaryColourChangeListener)
        viewBinding.doubleSeekBarPrimary.viewBinding.sbLeft.setOnSeekBarChangeListener(seekBarPrimaryBlackChangeListener)
        viewBinding.doubleSeekBarPrimary.viewBinding.sbRight.setOnSeekBarChangeListener(seekBarPrimaryWhiteChangeListener)

        viewBinding.sbColorSecondary.setOnSeekBarChangeListener(seekBarSecondaryColourChangeListener)
        viewBinding.doubleSeekBarSecondary.viewBinding.sbLeft.setOnSeekBarChangeListener(seekBarSecondaryBlackChangeListener)
        viewBinding.doubleSeekBarSecondary.viewBinding.sbRight.setOnSeekBarChangeListener(seekBarSecondaryWhiteChangeListener)

        viewBinding.sbColorBackground.setOnSeekBarChangeListener(seekBarBackgroundColourChangeListener)
        viewBinding.doubleSeekBarBackground.viewBinding.sbLeft.setOnSeekBarChangeListener(seekBarBackgroundBlackChangeListener)
        viewBinding.doubleSeekBarBackground.viewBinding.sbRight.setOnSeekBarChangeListener(seekBarBackgroundWhiteChangeListener)

        viewBinding.saveButton.setOnClickListener {
            colorHolder.putColours(primaryColour, secondaryColour, backgroundColour)
        }

    }

    private val seekBarPrimaryColourChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            primaryColour.pureColour = progressToPureColour(progress)
            setPrimaryColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarPrimaryWhiteChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            primaryColour.whiteness = progress.toFloat() / 100
            setPrimaryColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarPrimaryBlackChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            primaryColour.darkness = progress.toFloat() / 100
            setPrimaryColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private fun setPrimaryColor() {
        viewBinding.primaryText.setTextColor(primaryColour.getColor())
        viewBinding.backgroundText.setTextColor(primaryColour.getColor())
        viewBinding.saveButton.setTextColor(primaryColour.getColor())
    }



    private val seekBarSecondaryColourChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            secondaryColour.pureColour = progressToPureColour(progress)
            setSecondaryColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarSecondaryWhiteChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            secondaryColour.whiteness = progress.toFloat() / 100
            setSecondaryColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarSecondaryBlackChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            secondaryColour.darkness = progress.toFloat() / 100
            setSecondaryColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private fun setSecondaryColor() {
        viewBinding.secondaryText.setTextColor(secondaryColour.getColor())
        viewBinding.saveButton.setBackgroundColor(secondaryColour.getColor())
    }

    private val seekBarBackgroundColourChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            backgroundColour.pureColour = progressToPureColour(progress)
            setBackgroundColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarBackgroundWhiteChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            backgroundColour.whiteness = progress.toFloat() / 100
            setBackgroundColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private val seekBarBackgroundBlackChangeListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            backgroundColour.darkness = progress.toFloat() / 100
            setBackgroundColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    private fun setBackgroundColor() {
        viewBinding.llBackground.setBackgroundColor(backgroundColour.getColor())
    }

    private fun progressToPureColour(progress: Int): Int {
        val r : Int
        val g : Int
        val b : Int
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
                g = 255 - progress % 256
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
                b = 255 - progress % 256
            }
        }
        return Color.argb(alpha, r, g, b)
    }

    private fun pureColorToProgress(color : Int): Int {
        val r : Int = Color.red(color)
        val g : Int= Color.green(color)
        val b : Int = Color.blue(color)
        return when {
            r == 255 -> {
                if (g > b) {
                    g
                } else {
                    256 * 5 + 255 - b
                }
            }
            g == 255 -> {
                if (r > b) {
                    256 + 255 - r
                } else {
                    256 * 2 + b
                }
            }
            b == 255 -> {
                if (r > g) {
                    256 * 4 + r
                } else {
                    256 * 3 + 255 - g
                }
            }
            else -> {
                0
            }
        }
    }

    private fun setViewsColours() {
        val colors = colorHolder.getColors()

        primaryColour = Colour(colors.primary)
        setSeekBars(viewBinding.sbColorPrimary,
            viewBinding.doubleSeekBarPrimary.viewBinding.sbLeft,
            viewBinding.doubleSeekBarPrimary.viewBinding.sbRight,
            pureColorToProgress(primaryColour.pureColour).toFloat() / viewBinding.sbColorPrimary.max,
            primaryColour.darkness,
            primaryColour.whiteness
        )

        secondaryColour = Colour(colors.secondary)
        setSeekBars(viewBinding.sbColorSecondary,
            viewBinding.doubleSeekBarSecondary.viewBinding.sbLeft,
            viewBinding.doubleSeekBarSecondary.viewBinding.sbRight,
            pureColorToProgress(secondaryColour.pureColour).toFloat() / viewBinding.sbColorSecondary.max,
            secondaryColour.darkness,
            secondaryColour.whiteness
        )

        backgroundColour = Colour(colors.background)
        setSeekBars(viewBinding.sbColorBackground,
            viewBinding.doubleSeekBarBackground.viewBinding.sbLeft,
            viewBinding.doubleSeekBarBackground.viewBinding.sbRight,
            pureColorToProgress(backgroundColour.pureColour).toFloat() / viewBinding.sbColorBackground.max,
            backgroundColour.darkness,
            backgroundColour.whiteness
        )

        viewBinding.primaryText.setTextColor(colors.primary)
        viewBinding.secondaryText.setTextColor(colors.secondary)
        viewBinding.backgroundText.setTextColor(colors.primary)
        viewBinding.llBackground.setBackgroundColor(colors.background)
        viewBinding.saveButton.setBackgroundColor(colors.secondary)
        viewBinding.saveButton.setTextColor(colors.primary)
    }

    private fun setSeekBars(primarySeekBar : SeekBar, darknessSeekBar: SeekBar,
                            whitenessSeekBar: SeekBar, primProg : Float,
                            darkProg : Float, whiteProg : Float) {
        primarySeekBar.progress = (primarySeekBar.max * primProg).toInt()
        darknessSeekBar.progress = (darknessSeekBar.max * darkProg).toInt()
        whitenessSeekBar.progress = (whitenessSeekBar.max * whiteProg).toInt()

    }

}

