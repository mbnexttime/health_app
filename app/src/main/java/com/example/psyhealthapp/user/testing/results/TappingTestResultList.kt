package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable

class TappingTestResultList(val results: MutableList<TappingTestResult>) : Parcelable,
    TestResultList {
    constructor() : this(mutableListOf<TappingTestResult>()) {}

    fun putResult(result: TappingTestResult) {
        results.add(result)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReactionTestResultList> {
        override fun createFromParcel(parcel: Parcel): ReactionTestResultList {
            return ReactionTestResultList(parcel.createTypedArrayList(ReactionTestResult)!!)
        }

        override fun newArray(size: Int): Array<ReactionTestResultList?> {
            return arrayOfNulls(size)
        }
    }
}