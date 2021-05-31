package com.gk.emon.hadith.data.local

import androidx.room.TypeConverter
import com.gk.emon.hadith.model.CollectionMeta
import com.gk.emon.hadith.model.HadithMeta
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

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
    fun fromCollectionMeta(value: CollectionMeta): String {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun fromCollectionMetaList(list: List<CollectionMeta>): String {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun toCollectionMeta(value: String): CollectionMeta {
        val listType = object : TypeToken<CollectionMeta>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun toCollectionMetaList(value: String): List<CollectionMeta> {
        val listType = object : TypeToken<List<CollectionMeta>>() {}.type
        return Gson().fromJson(value, listType)
    }
    //Collection meta end



}