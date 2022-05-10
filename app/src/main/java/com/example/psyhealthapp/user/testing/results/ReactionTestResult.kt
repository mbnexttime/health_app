package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class ReactionTestResult(val date: Date, val result: Float) : Parcelable, TestResult() {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(date.time)
        parcel.writeFloat(result)
    }

    companion object CREATOR : Parcelable.Creator<ReactionTestResult> {
        override fun createFromParcel(parcel: Parcel): ReactionTestResult {
            val time = parcel.readLong()
            val result = parcel.readFloat()
            return ReactionTestResult(Date(time), result)
        }

        override fun newArray(size: Int): Array<ReactionTestResult?> {
            return arrayOfNulls(size)
        }
    }
}
