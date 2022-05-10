package com.example.psyhealthapp.debug.entries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.db.DBProvider
import com.example.psyhealthapp.user.testing.results.ReactionTestResult
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

private val tappingSampleResult = TappingTestResult(
    Date(),
    listOf(
        2.324F,
        2.449F,
        2.495F,
        2.539F,
        2.594F,
        2.66F,
        2.716F,
        2.766F,
        2.815F,
        2.873F,
        2.923F,
        2.975F,
        3.025F,
        3.073F,
        3.116F,
        3.186F,
        3.254F,
        3.319F,
        3.384F,
        3.451F,
        3.521F,
        3.595F,
        3.655F,
        3.727F,
        3.794F,
        3.854F,
        3.909F,
        3.966F,
        4.022F,
        4.089F,
        4.14F,
        4.191F,
        4.251F,
        4.32F,
        4.375F,
        4.438F,
        4.505F,
        4.572F,
        4.626F,
        4.685F,
        4.74F,
        4.789F,
        4.839F,
        4.903F,
        4.949F,
        5.004F,
        5.062F,
        5.121F,
        5.193F,
        5.264F,
        5.335F,
        5.401F,
        5.457F,
        5.506F,
        5.552F,
        5.595F,
        5.645F,
        5.716F,
        5.782F,
        5.838F
    ),
    listOf(
        2.696F,
        2.808F,
        2.858F,
        2.906F,
        2.968F,
        3.023F,
        3.089F,
        3.142F,
        3.19F,
        3.236F,
        3.299F,
        3.375F,
        3.452F,
        3.501F,
        3.57F,
        3.643F,
        3.696F,
        3.743F,
        3.79F,
        3.845F,
        3.897F,
        3.955F,
        4.019F,
        4.07F,
        4.114F,
        4.166F,
        4.216F,
        4.255F,
        4.3F,
        4.345F,
        4.396F,
        4.464F,
        4.535F,
        4.599F,
        4.662F,
        4.726F,
        4.785F,
        4.833F,
        4.887F,
        4.94F,
        4.997F,
        5.045F,
        5.095F,
        5.156F,
        5.206F,
        5.267F,
        5.311F,
        5.359F,
        5.41F,
        5.462F,
        5.516F,
        5.574F,
        5.637F,
        5.69F,
        5.75F,
        5.803F,
        5.867F,
        5.917F,
        5.961F,
        6.003F
    )
)

@AndroidEntryPoint
class DebugAddEntriesToDBFragment : Fragment(R.layout.debug_add_entries_to_db) {
    companion object {
        private val DB_TAG = "TappingResults"
    }

    @Inject
    lateinit var model: TestResultsHolder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reactionResultTextView =
            view.findViewById<TextView>(R.id.editTextTextReactionTestResult)
        val reactionDateTextView = view.findViewById<TextView>(R.id.editTextTextReactionTestDate)

        view.findViewById<Button>(R.id.add_entry_about_reaction_test).setOnClickListener {
            val words = reactionDateTextView.text.split(' ')
            model.putReactionTestResult(
                ReactionTestResult(
                    Date(words[0].toInt(), words[1].toInt(), words[2].toInt()),
                    reactionResultTextView.text.toString().toFloatOrNull() ?: 350F
                )
            )
        }
        view.findViewById<Button>(R.id.add_entry_about_tapping_test).setOnClickListener {
            model.putTappingTestResult(tappingSampleResult)
        }
    }
}