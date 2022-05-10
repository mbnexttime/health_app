package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable

class ReactionTestResultList(val results: MutableList<ReactionTestResult>) : Parcelable {
    constructor() : this(mutableListOf<ReactionTestResult>()) {}

    fun putResult(result: ReactionTestResult) {
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