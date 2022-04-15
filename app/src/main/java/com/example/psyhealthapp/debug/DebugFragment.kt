package com.example.psyhealthapp.debug

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.FragmentHolder

class DebugFragment : Fragment(R.layout.debug_fragment) {
    /**
     * Add your fragments here for debug purposes
     */
    private val fragments: ArrayList<FragmentHolder> = arrayListOf(
        FragmentHolder {
            return@FragmentHolder ComplexTestReactionFragment()
        },
    )


    private var currentFragment: Fragment? = null

    private lateinit var buttonsContainer: ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonsContainer = view.findViewById(R.id.fragment_list)

        fragments.forEach { holder: FragmentHolder ->
            addButton(holder)
        }
    }

    private fun addButton(holder: FragmentHolder) {
        val view = TextView(activity)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            requireContext().resources.getDimensionPixelSize(R.dimen.debug_fragment_chooser_button_width)
        )
        view.gravity = Gravity.CENTER
        view.textSize =
            requireContext().resources.getDimension(R.dimen.debug_fragment_chooser_button_text_size)
        view.text = holder.getFragment().javaClass.simpleName

        view.setOnClickListener {
            val fragment = holder.getFragment()
            val localCurrentFragment = currentFragment

            if (localCurrentFragment != null) {
                requireActivity().supportFragmentManager.beginTransaction().remove(localCurrentFragment).commit()
            }

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.debug_fragment_holder, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

            currentFragment = fragment
        }

        buttonsContainer.addView(view)
    }
}