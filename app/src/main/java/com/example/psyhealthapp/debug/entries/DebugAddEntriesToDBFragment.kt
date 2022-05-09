package com.example.psyhealthapp.debug.entries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.db.DBProvider

class DebugAddEntriesToDBFragment : Fragment(R.layout.debug_add_entries_to_db) {
    companion object {
        private val DB_TAG = "TappingResults"
    }

//    private val db : DB by lazy(LazyThreadSafetyMode.NONE) {
//        dbProvider.getDB(DB_TAG)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}