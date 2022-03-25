package com.example.psyhealthapp.db

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class DBProvider @Inject constructor(
    private val activity: AppCompatActivity,
) {
    private val sp: SharedPreferences by lazy {
        activity.getSharedPreferences(TAG, MODE_PRIVATE)
    }

    private val dbMap: HashMap<String, DB> = HashMap()

    fun getDB(tag: String): DB {
        return getOrCreateAndPut(tag)
    }

    private fun getOrCreateAndPut(tag: String): DB {
        val localDB = dbMap[tag]
        return if (localDB == null) {
            val createdDB = DBImpl(sp, tag)
            dbMap[tag] = createdDB
            createdDB
        } else {
            localDB
        }
    }

    companion object {
        private val TAG = "PsyHealthApp"
    }
}