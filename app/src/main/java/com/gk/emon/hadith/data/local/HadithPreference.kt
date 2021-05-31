package com.gk.emon.hadith.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HadithPreference @Inject constructor(@ApplicationContext context : Context){
    private val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getString(key :String): String{
        return preference.getString(key, "")?:""
    }
    fun setString(key: String,value:String) {
        preference.edit().putString(key, value).apply()
    }
}