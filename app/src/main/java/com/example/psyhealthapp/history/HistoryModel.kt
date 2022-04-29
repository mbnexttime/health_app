package com.example.psyhealthapp.history

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psyhealthapp.db.DB
import com.example.psyhealthapp.db.DBProvider
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryModel @Inject constructor(
    private val dbProvider: DBProvider,
) : ViewModel() {
    private val db: DB by lazy(LazyThreadSafetyMode.NONE) {
        dbProvider.getDB(DB_TAG)
    }

    private val handler = Handler(Looper.getMainLooper())

    interface HistoriesUpdateListeners {
        fun onHistoriesUpdate(historyList: HistoryList)

        fun onDraftUpdate(draft: String)
    }

    private var counter: Int = INVALID_COUNTER
    private var initialized: Boolean = false
    private val listeners = HashSet<HistoriesUpdateListeners>()

    private var historyList: HistoryList? = null
    private var draft: String? = null
    private val pendingHistories: HistoryList = HistoryList(ArrayList())

    fun getDraft(): String? {
        return draft
    }

    fun putDraft(draft: String) {
        this.draft = draft
        notifyDraft(draft)
        if (!initialized) {
            return
        }
        saveDraftToDB()
    }

    fun getHistories(): HistoryList? {
        return historyList
    }

    fun putHistory(history: History) {
        if (!initialized) {
            pendingHistories.addHistory(history)
            notifyHistories(pendingHistories)
            return
        }
        historyList?.let {
            it.addHistory(history)
            notifyHistories(it)
        }
        saveHistoriesToDB()
    }

    private fun saveDraftToDB() {
        viewModelScope.launch {
            draft?.let {
                db.putString(DRAFT_TAG, it)
            }
        }
    }

    private fun saveHistoriesToDB() {
        historyList?.let {
            counter++
            val localCounter = counter
            viewModelScope.launch {
                db.putStringsAsync(
                    listOf(
                        Pair(HISTORIES_TAG + localCounter, Gson().toJson(it)),
                        Pair(COUNTER_TAG, localCounter.toString())
                    )
                )
            }
        }
    }

    fun initialize() {
        if (initialized) {
            return
        }


        viewModelScope.launch {
            counter = db.getString(COUNTER_TAG)?.toInt() ?: INVALID_COUNTER
            val server = db.getParcelable(HISTORIES_TAG + counter, HistoryList::class.java)
            val draft = db.getString(DRAFT_TAG)

            handler.post {
                if (server != null) {
                    pendingHistories.histories.forEach {
                        server.addHistory(it)
                    }
                    historyList = server
                    notifyHistories(server)
                    saveHistoriesToDB()
                } else {
                    historyList = pendingHistories
                    notifyHistories(pendingHistories)
                    saveHistoriesToDB()
                }
                this@HistoryModel.draft = draft
                saveDraftToDB()
                initialized = true
            }
        }
    }

    fun addListenerAndNotify(listener: HistoriesUpdateListeners) {
        listeners.add(listener)
        historyList?.let {
            listener.onHistoriesUpdate(it)
        }
        draft?.let {
            listener.onDraftUpdate(it)
        }
    }

    fun removeListener(listener: HistoriesUpdateListeners) {
        listeners.remove(listener)
    }

    private fun notifyHistories(histories: HistoryList) {
        listeners.forEach { it.onHistoriesUpdate(histories) }
    }

    private fun notifyDraft(draft: String) {
        listeners.forEach { it.onDraftUpdate(draft) }
    }

    companion object {
        private val DB_TAG = "history"
        private val DRAFT_TAG = "history_draft"
        private val HISTORIES_TAG = "histories"
        private val COUNTER_TAG = "db_counter"
        private val INVALID_COUNTER = 0
    }
}