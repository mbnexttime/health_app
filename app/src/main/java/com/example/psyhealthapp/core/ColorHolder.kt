package com.example.psyhealthapp.core


import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psyhealthapp.R
import com.example.psyhealthapp.db.DB
import com.example.psyhealthapp.db.DBProvider
import com.example.psyhealthapp.settings.Colour
import com.example.psyhealthapp.settings.ColourSet
import com.example.psyhealthapp.util.LocalDateTimeAdapter
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorHolder @Inject constructor(
    private val dbProvider: DBProvider
) : ViewModel() {
    private val db: DB by lazy(LazyThreadSafetyMode.NONE) {
        dbProvider.getDB(DB_TAG)
    }

    private var colourSet : ColourSet = ColourSet(DEFAULT_PRIMARY_COLOR, DEFAULT_SECONDARY_COLOR, DEFAULT_BACKGROUND_COLOR)

    private var initialized = false

    fun putColours(primary: Colour, secondary: Colour, background : Colour) {
        colourSet.primary = primary.getColor()
        colourSet.secondary = secondary.getColor()
        colourSet.background = background.getColor()
        saveColors()
    }

    fun getColors(): ColourSet {
        return colourSet
    }

    private fun saveColors() {
        viewModelScope.launch {
            db.putStringsAsync(
                listOf(
                    Pair(DB_PRIMARY_TAG, gson.toJson(colourSet.primary)),
                    Pair(DB_SECONDARY_TAG, gson.toJson(colourSet.secondary)),
                    Pair(DB_BACKGROUND_TAG, gson.toJson(colourSet.background))
                )
            )
        }
    }

    fun initialize() {
        if (initialized) {
            return
        }

        viewModelScope.launch {
            colourSet.primary = db.getString(DB_PRIMARY_TAG)?.toInt() ?: DEFAULT_PRIMARY_COLOR
            colourSet.secondary = db.getString(DB_SECONDARY_TAG)?.toInt() ?: DEFAULT_SECONDARY_COLOR
            colourSet.background = db.getString(DB_BACKGROUND_TAG)?.toInt() ?: DEFAULT_BACKGROUND_COLOR

            handler.post {
                saveColors()
                initialized = true
                println("Here")
            }
        }
    }


    companion object {
        private const val DB_TAG = "colours"

        private const val DB_BACKGROUND_TAG = "background_colour"
        private const val DB_PRIMARY_TAG = "primary_colour"
        private const val DB_SECONDARY_TAG = "secondary_colour"

        private const val DEFAULT_PRIMARY_COLOR = R.color.black
        private const val DEFAULT_SECONDARY_COLOR = R.color.dark_grey
        private const val DEFAULT_BACKGROUND_COLOR = R.color.white

        private val handler = Handler(Looper.getMainLooper())
        private val gson = GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()
    }
}