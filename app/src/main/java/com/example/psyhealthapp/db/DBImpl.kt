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

    override fun <T : Parcelable> getParcelable(tag: String, cl: Class<T>): T? {
        val json = sp.getString(tag + spTag, null)
        return try {
            if (json != null)
                Gson().fromJson(json, cl)
            else null
        } catch (_: JsonSyntaxException) {
            null
        }
    }

    override fun putInt(tag: String, value: Int) {
        sp.edit().putInt(tag + spTag, value).apply()
    }

    override fun getInt(tag: String): Int? {
        if (!sp.contains(tag + spTag)) {
            return null
        }
        return sp.getInt(tag + spTag, 0)
    }

    override fun putString(tag: String, value: String) {
        sp.edit().putString(tag + spTag, value).apply()
    }

    override fun getString(tag: String): String? {
        return sp.getString(tag + spTag, null)
    }

    override fun putBoolean(tag: String, value: Boolean) {
        sp.edit().putBoolean(tag + spTag, value).apply()
    }

    override fun getBoolean(tag: String): Boolean? {
        if (!sp.contains(tag + spTag)) {
            return null
        }
        return sp.getBoolean(tag + spTag, false)
    }
}
