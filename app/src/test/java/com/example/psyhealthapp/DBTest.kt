package com.example.psyhealthapp

import android.os.Parcel
import android.os.Parcelable
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.psyhealthapp.db.DBProvider
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DBTest : TestCase() {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(HealthAppMainActivity::class.java)

    private val data = Data(1, "2")


    @Test
    fun checkDbSavesWhileActivityAlive() {
        scenarioRule.scenario.onActivity {
            val dbProvider = DBProvider(it)
            val db = dbProvider.getDB(TAG)
            db.putParcelable(TAG, data)
            val parcel = db.getParcelable(TAG, Data::class.java)
            assertEquals(data, parcel)
        }
    }

    @Test
    fun checkDbSavesWhileActivityNotAlive() {
        scenarioRule.scenario.onActivity {
            val dbProvider = DBProvider(it)
            val db = dbProvider.getDB(TAG)
            db.putParcelable(TAG, data)
        }

        scenarioRule.scenario.recreate()

        scenarioRule.scenario.onActivity {
            val dbProvider = DBProvider(it)
            val db = dbProvider.getDB(TAG)
            val parcel = db.getParcelable(TAG, Data::class.java)
            assertEquals(data, parcel)
        }
    }

    companion object {
        private val TAG = "TEST_TAG"
    }
}

data class Data(
    val num1: Int,
    val string1: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(num1)
        dest?.writeString(string1)
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}