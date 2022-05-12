package com.example.psyhealthapp.core

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.databinding.EmptyContentPlaceholderBinding

class EmptyContentFragment : Fragment(R.layout.empty_content_placeholder) {
    private val viewBinding by viewBinding(EmptyContentPlaceholderBinding::bind)

    companion object {
        private const val TEXT = "text"
        private const val IMG = "img"

        fun newInstance(text: Int, img: Int): EmptyContentFragment {
            val emptyContentFragment = EmptyContentFragment()
            val arguments = Bundle()
            arguments.putInt(TEXT, text)
            arguments.putInt(IMG, img)
            emptyContentFragment.arguments = arguments
            return emptyContentFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val text = it.getInt(TEXT)
            val img = it.getInt(IMG)
            viewBinding.text.text = getText(text)
            viewBinding.image.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), img)
            )
        }
    }
}