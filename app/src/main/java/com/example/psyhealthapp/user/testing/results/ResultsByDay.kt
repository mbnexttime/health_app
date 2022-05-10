package com.example.psyhealthapp.user.testing.results;

import android.os.Parcel
import android.os.Parcelable
import java.util.Date;

class ResultsByDay(val data: Map<Date, Int>) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLongArray(data.keys.map { it.time }.toLongArray())
        parcel.writeIntArray(data.values.toIntArray())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultsByDay> {
        override fun createFromParcel(parcel: Parcel): ResultsByDay {
            val keys = parcel.createLongArray()!!.toList()
            val values = parcel.createIntArray()!!.toList()
            return ResultsByDay(
                keys.mapIndexed { it, i -> Pair(Date(i), values[it]) }.toMap()
            )
        }

        override fun newArray(size: Int): Array<ResultsByDay?> {
            return arrayOfNulls(size)
        }
    }
}
