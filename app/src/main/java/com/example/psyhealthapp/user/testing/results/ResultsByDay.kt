package com.example.psyhealthapp.user.testing.results;

import android.os.Parcel
import android.os.Parcelable
import hilt_aggregated_deps._com_example_psyhealthapp_HealthAppMainActivity_GeneratedInjector
import java.time.LocalDate
import java.util.*

class ResultsByDay(val data: SortedMap<LocalDate, Int>) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(data.keys.toTypedArray())
        parcel.writeIntArray(data.values.toIntArray())
    }

    fun clone(): ResultsByDay {
        val dataClone = data.toSortedMap()
        return ResultsByDay(dataClone)
    }

    fun addResult(date: LocalDate, cnt: Int = 1) {
        data[date] = data.getOrDefault(date, 0) + cnt
    }

    override fun describeContents(): Int {
        return 0
    }

    fun mergeWith(other: ResultsByDay) {
        other.data.forEach {
            addResult(it.key, it.value)
        }
    }

    companion object CREATOR : Parcelable.Creator<ResultsByDay> {
        override fun createFromParcel(parcel: Parcel): ResultsByDay {
            val keys = parcel.readSerializable() as Array<LocalDate>
            val values = parcel.createIntArray()!!
            return ResultsByDay(
                keys.mapIndexed { it, i -> Pair(i, values[it]) }.toMap().toSortedMap()
            )
        }

        override fun newArray(size: Int): Array<ResultsByDay?> {
            return arrayOfNulls(size)
        }
    }
}
