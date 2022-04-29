package com.example.psyhealthapp.db

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson

class ParcelableWriter(
    appContext: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val count = inputData.getString(WORKER_COUNT)?.toInt() ?: 0
        val spTag = inputData.getString(WORKER_DB_TAG)
        val sp = applicationContext.getSharedPreferences(spTag, Context.MODE_PRIVATE)
        for (i in 0 until count) {
            val tag = inputData.getString(WORKER_TAG + i)
            val data = inputData.getString(WORKER_DATA + i)
            
            sp.edit().putString(tag, data).apply()
        }
        
        return Result.success()
    }

    companion object {
        val WORKER_DB_TAG = "worker_db_tag"
        val WORKER_TAG = "worker_tag"
        val WORKER_DATA = "worker_data"
        val WORKER_COUNT = "worker_count"
    }
}