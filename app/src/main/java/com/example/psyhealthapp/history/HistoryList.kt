package com.example.psyhealthapp.history

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class HistoryList(
    val histories: ArrayList<History>,
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(History)!!) {
    }
    
    fun addHistory(history: History) {
        histories.add(0, history)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(histories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HistoryList> {
        override fun createFromParcel(parcel: Parcel): HistoryList {
            return HistoryList(parcel)
        }

        override fun newArray(size: Int): Array<HistoryList?> {
            return arrayOfNulls(size)
        }
    }

}