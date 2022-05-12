package com.example.psyhealthapp.core

import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psyhealthapp.db.DB
import com.example.psyhealthapp.db.DBProvider
import com.example.psyhealthapp.user.testing.results.*
import com.example.psyhealthapp.util.LocalDateAdapter
import com.google.gson.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestResultsHolder @Inject constructor(
    private val dbProvider: DBProvider
) : ViewModel() {
    private val db: DB by lazy(LazyThreadSafetyMode.NONE) {
        dbProvider.getDB(DB_TAG)
    }

    class Keeper<T : TestResult>(
        private val TEST_TAG: String,
        private val COUNTER_TAG: String,
        val db: DB,
        val getServer: (String) -> MutableList<T>?,
        val Cons: (MutableList<T>) -> Parcelable
    ) : ViewModel() {
        private var counter = INVALID_COUNTER
        private var initialized = false
        private val pendingList = mutableListOf<T>()
        private var resultList: MutableList<T>? = null
        private val resultsByDay = ResultsByDay(sortedMapOf())

        fun getResultsByDay(): ResultsByDay {
            return resultsByDay
        }

        fun getTestResults(): MutableList<T> {
            return resultList ?: pendingList
        }

        fun putTestResult(result: T) {
            resultsByDay.addResult(result.date)
            if (!initialized) {
                pendingList.add(result)
                return
            }
            resultList?.add(result)
            saveTestResultsToDB()
        }

        private fun saveTestResultsToDB() {
            resultList?.let {
                counter++
                val localCounter = counter
                viewModelScope.launch {
                    db.putStringsAsync(
                        listOf(
                            Pair(TEST_TAG + localCounter, gson.toJson(Cons(it))),
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
                    server?.forEach {
                        resultsByDay.addResult(it.date)
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
        db,
        {
            db.getParcelable(it, ReactionTestResultList::class.java, gson)?.results
        },
        {
            ReactionTestResultList(it)
        }
    )

    private val complexReactionTestResultsKeeper = Keeper<ReactionTestResult>(
        COMPLEX_REACTION_TAG,
        COMPLEX_REACTION_COUNTER_TAG,
        db,
        {
            db.getParcelable(it, ReactionTestResultList::class.java, gson)?.results
        },
        {
            ReactionTestResultList(it)
        }
    )

    private val movingReactionTestResultKeeper = Keeper<ReactionTestResult>(
        MOVING_TAG,
        MOVING_COUNTER_TAG,
        db,
        {
            db.getParcelable(it, ReactionTestResultList::class.java, gson)?.results
        },
        {
            ReactionTestResultList(it)
        }
    )

    private val tappingTestResultsKeeper = Keeper<TappingTestResult>(
        TAPPING_TAG,
        TAPPING_COUNTER_TAG,
        db,
        {
            db.getParcelable(it, TappingTestResultList::class.java)?.results
        },
        {
            TappingTestResultList(it)
        }
    )

    fun initialize() {
        movingReactionTestResultKeeper.initialize()
        reactionTestResultsKeeper.initialize()
        complexReactionTestResultsKeeper.initialize()
        tappingTestResultsKeeper.initialize()
    }

    fun putMovingReactionTestResult(result: ReactionTestResult) {
        movingReactionTestResultKeeper.putTestResult(result)
    }

    fun getMovingReactionTestResults(): ReactionTestResultList {
        return ReactionTestResultList(movingReactionTestResultKeeper.getTestResults())
    }

    fun putReactionTestResult(result: ReactionTestResult) {
        reactionTestResultsKeeper.putTestResult(result)
    }

    fun getReactionTestResults(): ReactionTestResultList {
        return ReactionTestResultList(reactionTestResultsKeeper.getTestResults())
    }

    fun putComplexReactionTestResult(result: ReactionTestResult) {
        complexReactionTestResultsKeeper.putTestResult(result)
    }

    fun getComplexReactionTestResults(): ReactionTestResultList {
        return ReactionTestResultList(complexReactionTestResultsKeeper.getTestResults())
    }

    fun putTappingTestResult(result: TappingTestResult) {
        tappingTestResultsKeeper.putTestResult(result)
    }

    fun getLastTappingTestResult(): TappingTestResult? {
        return tappingTestResultsKeeper.getTestResults().lastOrNull()
    }

    fun getResultsCountByDays(): ResultsByDay {
        val resultsByDay = tappingTestResultsKeeper.getResultsByDay().clone()
        resultsByDay.mergeWith(reactionTestResultsKeeper.getResultsByDay())
        resultsByDay.mergeWith(complexReactionTestResultsKeeper.getResultsByDay())
        resultsByDay.mergeWith(movingReactionTestResultKeeper.getResultsByDay())

        return resultsByDay
    }

    companion object {
        private const val DB_TAG = "testingResults"

        private const val REACTION_TAG = "reaction"
        private const val REACTION_COUNTER_TAG = "reaction_counter"

        private const val COMPLEX_REACTION_TAG = "complex_reaction"
        private const val COMPLEX_REACTION_COUNTER_TAG = "complex_reaction_counter"

        private const val TAPPING_TAG = "tapping"
        private const val TAPPING_COUNTER_TAG = "tapping_counter"

        private const val MOVING_TAG = "moving"
        private const val MOVING_COUNTER_TAG = "moving_counter"

        private const val INVALID_COUNTER = 0
        private val handler = Handler(Looper.getMainLooper())
        private val gson = GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()
    }
}