package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

fun LocalDate.writeToParcel(parcel: Parcel) {
    parcel.writeInt(this.year)
    parcel.writeInt(this.monthValue)
    parcel.writeInt(this.dayOfMonth)
}

fun getLocalDateFromParcel(parcel: Parcel): LocalDate {
    val year = parcel.readInt()
    val month = parcel.readInt()
    val day = parcel.readInt()
    println(year)
    println(month)
    println(day)
    return LocalDate.of(year, month, day)
}

class ReactionTestResult(date: LocalDate, val result: Float) : Parcelable, TestResult(date) {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        println("ReactionTestResult::writeToParcel()")
        date.writeToParcel(parcel)
        parcel.writeFloat(result)
    }

    companion object CREATOR : Parcelable.Creator<ReactionTestResult> {
        override fun createFromParcel(parcel: Parcel): ReactionTestResult {
            val localDate = getLocalDateFromParcel(parcel)
            val result = parcel.readFloat()
            return ReactionTestResult(localDate, result)
        }

        override fun newArray(size: Int): Array<ReactionTestResult?> {
            return arrayOfNulls(size)
        }
    }
}
