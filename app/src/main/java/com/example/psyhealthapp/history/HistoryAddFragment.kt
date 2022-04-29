package com.example.psyhealthapp.history

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.HistoryAddViewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryAddFragment : Fragment(R.layout.history_add_view) {
    @Inject
    lateinit var model: HistoryModel
    
    @Inject
    lateinit var navigator: NavController

    private lateinit var binding: HistoryAddViewBinding
    
    private val modelListener = object: HistoryModel.HistoriesUpdateListeners {
        override fun onHistoriesUpdate(historyList: HistoryList) = Unit

        override fun onDraftUpdate(draft: String) {
            if (draft.isNotEmpty() && binding.historyAddEditText.text.toString().isEmpty()) {
                binding.historyAddEditText.text = Editable.Factory.getInstance().newEditable(draft)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HistoryAddViewBinding.bind(view)
        
        binding.historyAddContinueButton.setOnClickListener {
            val text = binding.historyAddEditText.editableText.toString()
            if (text.isEmpty()) {
                Toast.makeText(context, R.string.history_add_error_text, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            model.putHistory(History(text, System.currentTimeMillis()))
            model.putDraft("")
            navigator.navigate(R.id.navigation_to_history_show)
        }
        
        binding.historyAddEditText.addTextChangedListener { 
            model.putDraft(it.toString())
        }
        
        binding.historyAddCancelButton.setOnClickListener { 
            navigator.popBackStack()
        }
        
        model.addListenerAndNotify(modelListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        model.removeListener(modelListener)
    }
}