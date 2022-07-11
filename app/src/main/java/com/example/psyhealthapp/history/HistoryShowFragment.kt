package com.example.psyhealthapp.history

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.ColorHolder
import com.example.psyhealthapp.databinding.HistoryShowViewBinding
import com.example.psyhealthapp.util.RecyclerViewAdapterWithDelegates
import com.example.psyhealthapp.util.RecyclerViewItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryShowFragment : Fragment(R.layout.history_show_view) {
    private lateinit var binding: HistoryShowViewBinding

    @Inject
    lateinit var colorHolder: ColorHolder

    @Inject
    lateinit var model: HistoryModel
    
    @Inject
    lateinit var navController: NavController

    private val modelListener = object : HistoryModel.HistoriesUpdateListeners {
        override fun onHistoriesUpdate(historyList: HistoryList) {
            if (binding.historyShowContentContainer.visibility == View.GONE) {
                binding.historyShowContentContainer.visibility = View.VISIBLE
                binding.historyShowProgressLoader.visibility = View.GONE
            }
            val items = ArrayList<RecyclerViewItem>()
            historyList.histories.withIndex().forEach {
                items.add(HistoryRecyclerViewItem(it.value))
                if (it.index != historyList.histories.size - 1) {
                    items.add(HistoryDelimRecyclerViewItem())
                }
            }
            adapter.items = items
            adapter.notifyDataSetChanged()
        }

        override fun onDraftUpdate(draft: String) = Unit
    }

    private lateinit var adapter: RecyclerViewAdapterWithDelegates

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HistoryShowViewBinding.bind(view)
        setViewsColours()
        binding.historyShowContentContainer.visibility = View.GONE
        binding.historyShowList.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerViewAdapterWithDelegates(
            listOf(
                HistoryRecyclerViewAdapterDelegate(context),
                HistoryDelimRecyclerViewAdapterDelegate(),
            ),
            listOf()
        )
        binding.historyShowList.adapter = adapter
        binding.historyShowAddButton.setOnClickListener { 
            navController.navigate(R.id.navigation_to_history_add)
        }
        model.addListenerAndNotify(modelListener)
    }

    private fun setViewsColours() {
        val colors = colorHolder.getColors()
        binding.historyShowAddButton.setTextColor(colors.primary)
        binding.historyShowAddButton.setBackgroundColor(colors.secondary)
        binding.historyShowContainer.setBackgroundColor(colors.background)
        for (child in binding.historyShowList.children) {
            val view = child as LinearLayout
            (view.getChildAt(0) as TextView).setTextColor(colors.primary)
            (view.getChildAt(0) as TextView).setBackgroundColor(colors.secondary)
            (view.getChildAt(1) as TextView).setTextColor(colors.secondary)
            (view.getChildAt(1) as TextView).setBackgroundColor(colors.background)
        }

    }
}
