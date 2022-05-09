package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable

class TappingTestResult(
    val leftHandMoments: List<Float>,
    val rightHandMoments: List<Float>
) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloatArray(leftHandMoments.toFloatArray())
        parcel.writeFloatArray(rightHandMoments.toFloatArray())
    }

    companion object CREATOR : Parcelable.Creator<TappingTestResult> {
        override fun createFromParcel(parcel: Parcel): TappingTestResult {
            val leftHandMoments = parcel.createFloatArray()!!.toList()
            val rightHandMoments = parcel.createFloatArray()!!.toList()
            return TappingTestResult(leftHandMoments, rightHandMoments)
        }

        override fun newArray(size: Int): Array<TappingTestResult?> {
            return arrayOfNulls(size)
        }
    }
}