package com.gk.emon.hadith.data.local

import androidx.room.TypeConverter
import com.gk.emon.hadith.model.HadithCollectionMeta
import com.gk.emon.hadith.model.HadithBookMeta
import com.gk.emon.hadith.model.HadithMeta
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object RoomConverter {

    //Hadith meta start
    @TypeConverter
    fun fromHadithMeta(value: HadithMeta): String {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun fromHadithMetaList(list: List<HadithMeta>): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun toHadithMeta(value: String): HadithMeta {
        val listType = object : TypeToken<HadithMeta>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun toHadithMetaList(value: String): List<HadithMeta> {
        val listType = object : TypeToken<List<HadithMeta>>() {}.type
        return Gson().fromJson(value, listType)
    }
    //Hadith meta start


    //Collection meta start
    @TypeConverter
    fun fromCollectionMeta(value: HadithCollectionMeta): String {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun fromCollectionMetaList(list: List<HadithCollectionMeta>): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun toCollectionMeta(value: String): HadithCollectionMeta {
        val listType = object : TypeToken<HadithCollectionMeta>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun toCollectionMetaList(value: String): List<HadithCollectionMeta> {
        val listType = object : TypeToken<List<HadithCollectionMeta>>() {}.type
        return Gson().fromJson(value, listType)
    }
    //Collection meta end


    //Hadith book meta start
    @TypeConverter
    fun fromHadithBookMeta(value: HadithBookMeta): String {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun fromHadithBookMetaList(list: List<HadithBookMeta>): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun toHadithBookMeta(value: String): HadithBookMeta {
        val listType = object : TypeToken<HadithBookMeta>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun toHadithBookMetaList(value: String): List<HadithBookMeta> {
        val listType = object : TypeToken<List<HadithBookMeta>>() {}.type
        return Gson().fromJson(value, listType)
    }
    //Hadith book start



}