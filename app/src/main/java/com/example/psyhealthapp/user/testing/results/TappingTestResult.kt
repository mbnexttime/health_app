package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class TappingTestResult(
    val date: Date,
    val rightHandMoments: List<Float>,
    val leftHandMoments: List<Float>,
) : Parcelable, TestResult() {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(date.time)
        parcel.writeFloatArray(rightHandMoments.toFloatArray())
        parcel.writeFloatArray(leftHandMoments.toFloatArray())
    }

    companion object CREATOR : Parcelable.Creator<TappingTestResult> {
        override fun createFromParcel(parcel: Parcel): TappingTestResult {
            val time = parcel.readLong()
            val rightHandMoments = parcel.createFloatArray()!!.toList()
            val leftHandMoments = parcel.createFloatArray()!!.toList()
            return TappingTestResult(Date(time), rightHandMoments, leftHandMoments)
        }

        override fun newArray(size: Int): Array<TappingTestResult?> {
            return arrayOfNulls(size)
        }
    }
}