package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

class ReactionTestResult(date: LocalDateTime, val result: Float) : Parcelable, TestResult(date) {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(date)
        parcel.writeFloat(result)
    }

    companion object CREATOR : Parcelable.Creator<ReactionTestResult> {
        override fun createFromParcel(parcel: Parcel): ReactionTestResult {
            val date = parcel.readSerializable() as LocalDateTime
            val result = parcel.readFloat()
            return ReactionTestResult(date, result)
        }

        override fun newArray(size: Int): Array<ReactionTestResult?> {
            return arrayOfNulls(size)
        }
    }
}
