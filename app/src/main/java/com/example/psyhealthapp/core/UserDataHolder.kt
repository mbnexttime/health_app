package com.example.psyhealthapp.core

import android.os.Parcelable
import com.example.psyhealthapp.db.DB
import com.example.psyhealthapp.db.DBProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

enum class UserDataType(val tag: String) {
    NAME("name"),
    AGE("age"),
    SEX("sex"),
    EMAIL("email"),
    URI("uri"),
    BACK_R("r"),
    BACK_G("g"),
    BACK_B("b"),
}

@Singleton
class UserDataHolder @Inject constructor(
    private val dbProvider: Provider<DBProvider>,
) {
    private val db: DB by lazy(LazyThreadSafetyMode.NONE) {
        dbProvider.get().getDB(DB_TAG)
    }

    fun setUserData(type: UserDataType, data: Parcelable) {
        db.putParcelable(type.tag, data)
    }

    fun setUserData(type: UserDataType, data: Int) {
        db.putInt(type.tag, data)
    }

    fun setUserData(type: UserDataType, data: String) {
        db.putString(type.tag, data)
    }

    fun <T : Parcelable> getUserData(type: UserDataType, dataClass: Class<T>): T? {
        return db.getParcelable(type.tag, dataClass)
    }

    fun getUserDataInt(type: UserDataType): Int? {
        return db.getInt(type.tag)
    }

    fun getUserDataString(type: UserDataType): String? {
        return db.getString(type.tag)
    }

    companion object {
        private const val DB_TAG = "user_data"
    }
}