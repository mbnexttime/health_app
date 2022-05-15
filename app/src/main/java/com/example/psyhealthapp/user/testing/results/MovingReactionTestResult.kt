package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime

class MovingReactionTestResult(
    date: LocalDateTime,
    val minDiff: Float,
    val maxDiff: Float,
    val expected: Float,
    val dispersion: Float
) : TestResult(date), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readSerializable() as LocalDateTime,
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(date)
        parcel.writeFloat(minDiff)
        parcel.writeFloat(maxDiff)
        parcel.writeFloat(expected)
        parcel.writeFloat(dispersion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovingReactionTestResult> {
        override fun createFromParcel(parcel: Parcel): MovingReactionTestResult {
            return MovingReactionTestResult(parcel)
        }

        override fun newArray(size: Int): Array<MovingReactionTestResult?> {
            return arrayOfNulls(size)
        }
    }
}