package com.example.psyhealthapp.core

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psyhealthapp.db.DB
import com.example.psyhealthapp.db.DBProvider
import com.example.psyhealthapp.user.testing.results.*
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestResultsHolder @Inject constructor(
    private val dbProvider: DBProvider
) : ViewModel() {
    private val db: DB by lazy(LazyThreadSafetyMode.NONE) {
        dbProvider.getDB(DB_TAG)
    }

    class Keeper<T>(
        private val TEST_TAG: String,
        private val COUNTER_TAG: String,
        private val pendingList: MutableList<T>,
        private var resultList: MutableList<T>?,
        val db: DB,
        val getServer: (String) -> MutableList<T>?,
        val Cons: (MutableList<T>) -> TestResultList
    ) : ViewModel() {
        private var counter = INVALID_COUNTER
        private var initialized = false

        fun getTestResults(): MutableList<T> {
            return resultList ?: pendingList
        }

        fun putTestResult(result: T) {
            if (!initialized) {
                pendingList.add(result)
                return
            }
            resultList?.add(result)
            saveTestResultsToDB()
        }

        fun saveTestResultsToDB() {
            resultList?.let {
                counter++
                val localCounter = counter
                viewModelScope.launch {
                    db.putStringsAsync(
                        listOf(
                            Pair(TEST_TAG + localCounter, Gson().toJson(Cons(it))),
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
                val server = getServer(TEST_TAG + counter)

                handler.post {
                    resultList = if (server != null) {
                        pendingList.forEach {
                            server.add(it)
                        }
                        server
                    } else {
                        pendingList
                    }
                    saveTestResultsToDB()
                    initialized = true
                }
            }
        }
    }

    private val reactionTestResultsKeeper = Keeper<ReactionTestResult>(
        REACTION_TAG,
        REACTION_COUNTER_TAG,
        mutableListOf<ReactionTestResult>(),
        null,
        db,
        {
            db.getParcelable(it, ReactionTestResultList::class.java)?.results
        },
        {
            ReactionTestResultList(it)
        }
    )

    private val tappingTestResultsKeeper = Keeper<TappingTestResult>(
        TAPPING_TAG,
        TAPPING_COUNTER_TAG,
        mutableListOf<TappingTestResult>(),
        null,
        db,
        {
            db.getParcelable(it, TappingTestResultList::class.java)?.results
        },
        {
            TappingTestResultList(it)
        }
    )

    fun initialize() {
        reactionTestResultsKeeper.initialize()
        tappingTestResultsKeeper.initialize()
    }

    fun putReactionTestResult(result: ReactionTestResult) {
        reactionTestResultsKeeper.putTestResult(result)
    }

    fun getReactionTestResults(): ReactionTestResultList {
        return ReactionTestResultList(reactionTestResultsKeeper.getTestResults())
    }

    fun putTappingTestResult(result: TappingTestResult) {
        tappingTestResultsKeeper.putTestResult(result)
    }

    fun getLastTappingTestResult(): TappingTestResult? {
        return tappingTestResultsKeeper.getTestResults().lastOrNull()
    }

    fun getResultsCountByDays(): ResultsByDay {
        val ret = mutableMapOf<Date, Int>()
        getReactionTestResults().results.forEach {
            val testDay = Date(it.date.year, it.date.month, it.date.date)
            ret[testDay] = ret.getOrDefault(testDay, 0) + 1
        }
        return ResultsByDay(ret)
    }

    companion object {
        private const val DB_TAG = "testingResults"
        private const val REACTION_TAG = "reaction"
        private const val REACTION_COUNTER_TAG = "reaction_counter"
        private const val TAPPING_TAG = "tapping"
        private const val TAPPING_COUNTER_TAG = "tapping_counter"
        private const val INVALID_COUNTER = 0
        private val handler = Handler(Looper.getMainLooper())
    }
}