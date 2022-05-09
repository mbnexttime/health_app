package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable

class ReactionTestResult(val days: List<String>, val values: List<Float>) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(days)
        parcel.writeFloatArray(values.toFloatArray())
    }

    companion object CREATOR : Parcelable.Creator<ReactionTestResult> {
        override fun createFromParcel(parcel: Parcel): ReactionTestResult {
            val days = parcel.createStringArray()!!.toList()
            val values = parcel.createFloatArray()!!.toList()
            return ReactionTestResult(days, values)
        }

        override fun newArray(size: Int): Array<ReactionTestResult?> {
            return arrayOfNulls(size)
        }
    }
}
