package com.example.psyhealthapp.db

import android.os.Parcelable

interface DB {
    fun putParcelable(tag: String, parcel: Parcelable)

    fun <T : Parcelable> getParcelable(tag: String, cl: Class<T>): T?

    fun putInt(tag: String, value: Int)

    fun getInt(tag: String): Int?

    fun putString(tag: String, value: String)

    fun getString(tag: String): String?

    fun putBoolean(tag: String, value: Boolean)

    fun getBoolean(tag: String): Boolean?
}