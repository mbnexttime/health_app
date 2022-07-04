package com.example.psyhealthapp.settings

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.psyhealthapp.databinding.ViewDoubleSeekBarBinding

class DoubleSeekBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    val viewBinding = ViewDoubleSeekBarBinding.inflate(LayoutInflater.from(context), this)
}