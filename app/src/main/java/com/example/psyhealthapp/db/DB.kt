package com.example.psyhealthapp.db

import android.os.Parcelable

interface DB {
    fun putParcelable(tag: String, parcel: Parcelable)

    fun <T> getParcelable(tag: String, cl: Class<T>): T?
}