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
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.databinding.DebugAddEntriesToDbBinding

private val tappingSampleResult = TappingTestResult(
    LocalDate.now(),
    listOf(
        0.055F,
        0.181F,
        0.266F,
        0.338F,
        0.399F,
        0.466F,
        0.547F,
        0.614F,
        0.692F,
        0.778F,
        0.85F,
        0.929F,
        1.054F,
        1.144F,
        1.23F,
        1.315F,
        1.413F,
        1.509F,
        1.588F,
        1.68F,
        1.762F,
        1.845F,
        1.946F,
        2.048F,
        2.131F,
        2.218F,
        2.315F,
        2.445F,
        2.571F,
        2.661F,
        2.764F,
        2.853F,
        2.966F,
        3.055F,
        3.128F,
        3.21F,
        3.317F,
        3.412F,
        3.501F,
        3.596F,
        3.685F,
        3.776F,
        3.861F,
        3.949F,
        4.033F,
        4.129F,
        4.224F,
        4.303F,
        4.403F,
        4.479F,
        4.557F,
        4.644F,
        4.742F,
        4.867F,
        4.97F,
        5.061F,
        5.146F,
        5.24F,
        5.338F,
        5.424F,
        5.512F,
        5.611F,
        5.694F,
        5.808F,
        5.897F,
        5.992F,
        6.1F,
        6.19F,
        6.282F,
        6.356F,
        6.44F,
        6.537F,
        6.627F,
        6.723F,
        6.801F,
        6.898F,
        6.976F,
        7.049F,
        7.132F,
        7.226F,
        7.31F,
        7.398F,
        7.518F,
        7.615F,
        7.691F,
        7.79F,
        7.911F,
        7.999F,
        8.082F,
        8.195F,
        8.291F,
        8.39F,
        8.492F,
        8.606F,
        8.679F,
        8.77F,
        8.851F,
        8.943F,
        9.047F,
        9.121F,
        9.227F,
        9.304F,
        9.401F,
        9.487F,
        9.602F,
        9.715F,
        9.794F
    ),
    listOf(
        0.093F,
        0.276F,
        0.373F,
        0.469F,
        0.595F,
        0.714F,
        0.827F,
        0.905F,
        0.979F,
        1.055F,
        1.159F,
        1.271F,
        1.382F,
        1.494F,
        1.567F,
        1.67F,
        1.787F,
        1.867F,
        1.973F,
        2.047F,
        2.118F,
        2.211F,
        2.326F,
        2.4F,
        2.472F,
        2.549F,
        2.626F,
        2.728F,
        2.803F,
        2.885F,
        2.959F,
        3.035F,
        3.142F,
        3.262F,
        3.338F,
        3.407F,
        3.502F,
        3.589F,
        3.664F,
        3.734F,
        3.808F,
        3.878F,
        3.968F,
        4.062F,
        4.181F,
        4.258F,
        4.369F,
        4.448F,
        4.529F,
        4.615F,
        4.724F,
        4.835F,
        4.93F,
        5.019F,
        5.108F,
        5.2F,
        5.31F,
        5.426F,
        5.538F,
        5.656F,
        5.731F,
        5.834F,
        5.909F,
        5.981F,
        6.053F,
        6.159F,
        6.237F,
        6.319F,
        6.406F,
        6.522F,
        6.596F,
        6.672F,
        6.745F,
        6.855F,
        6.927F,
        7.002F,
        7.085F,
        7.151F,
        7.232F,
        7.308F,
        7.419F,
        7.511F,
        7.602F,
        7.676F,
        7.769F,
        7.854F,
        7.94F,
        8.026F,
        8.119F,
        8.208F,
        8.286F,
        8.392F,
        8.465F,
        8.543F,
        8.622F,
        8.702F,
        8.792F,
        8.867F,
        8.955F,
        9.027F,
        9.132F,
        9.245F,
        9.363F,
        9.476F,
        9.552F,
        9.629F,
        9.74F,
        9.851F
    )
)

@AndroidEntryPoint
class DebugAddEntriesToDBFragment : Fragment(R.layout.debug_add_entries_to_db) {
    @Inject
    lateinit var model: TestResultsHolder

    private val viewBinding by viewBinding(DebugAddEntriesToDbBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.addEntryAboutReactionTest.setOnClickListener {
            val words = viewBinding.editTextTextReactionTestDate.text.split(' ')
            val year = words[0].toIntOrNull() ?: 0
            val month = words[1].toIntOrNull() ?: 0
            val day = words[2].toIntOrNull() ?: 0

            model.putReactionTestResult(
                ReactionTestResult(
                    LocalDate.of(year, month, day),
                    viewBinding.editTextTextReactionTestResult.text.toString().toFloatOrNull()
                        ?: 350F
                )
            )
        }

        viewBinding.addEntryAboutComplexReactionTest.setOnClickListener {
            val words = viewBinding.editTextComplexTextReactionTestDate.text.split(' ')
            val year = words[0].toIntOrNull() ?: 0
            val month = words[1].toIntOrNull() ?: 0
            val day = words[2].toIntOrNull() ?: 0

            model.putComplexReactionTestResult(
                ReactionTestResult(
                    LocalDate.of(year, month, day),
                    viewBinding.editTextComplexTextReactionTestResult.text.toString()
                        .toFloatOrNull()
                        ?: 239F
                )
            )
        }

        viewBinding.addEntryAboutTappingTest.setOnClickListener {
            model.putTappingTestResult(tappingSampleResult)
        }
    }

}