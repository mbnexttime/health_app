package com.example.psyhealthapp.debug.entries

import androidx.lifecycle.ViewModel
import com.example.psyhealthapp.db.DB
import com.example.psyhealthapp.db.DBProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugAddEntriesToDBModel @Inject constructor(
    private val dbProvider: DBProvider
) : ViewModel() {
    private val db: DB by lazy(LazyThreadSafetyMode.NONE) {
        dbProvider.getDB(DB_TAG)
    }



    companion object {
        private val DB_TAG = "testingResults"
        private val TAPPING_TAG = "tapping"
        private val REACTION_TAG = "reaction"
    }
}