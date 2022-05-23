package com.example.psyhealthapp.user.testing.results

import android.os.Parcel
import android.os.Parcelable

class MovingReactionTestResultList(val results: MutableList<MovingReactionTestResult>) :
    TestResultList,
    Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovingReactionTestResultList> {
        override fun createFromParcel(parcel: Parcel): MovingReactionTestResultList {
            return MovingReactionTestResultList(parcel.createTypedArrayList(MovingReactionTestResult)!!)
        }

        override fun newArray(size: Int): Array<MovingReactionTestResultList?> {
            return arrayOfNulls(size)
        }
    }
}