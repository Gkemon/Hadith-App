package com.gk.emon.hadith.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hadith")
data class Hadith(
    val bookNumber: String,
    val chapterId: String,
    val collection: String,
    val hadith: List<HadithMeta>,
    @PrimaryKey val hadithNumber: String
){
    fun getProperBodyHadithEnglish(): String {
        return if (hadith.isNotEmpty()) {
            hadith[0].body
        } else ""
    }
    fun getProperBodyHadithArabic(): String {
        return if (hadith.isNotEmpty()) {
            hadith[1].body
        } else ""
    }
}