package com.example.psyhealthapp.db

import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

internal class DBImpl(
    private val sp: SharedPreferences,
    private val spTag: String,
) : DB {
    override fun putParcelable(tag: String, parcel: Parcelable) {
        val json = Gson().toJson(parcel)
        sp.edit().putString(tag + spTag, json).apply()

    }

    override fun <T> getParcelable(tag: String, cl: Class<T>): T? {
        val json = sp.getString(tag + spTag, null)
        return try {
            if (json != null)
                Gson().fromJson(json, cl)
            else null
        } catch (_: JsonSyntaxException) {
            null
        }
    }
}
