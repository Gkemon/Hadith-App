package com.gk.emon.hadith.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hadithBook")
data class HadithBook(
    val book: List<HadithBookMeta>,
    @PrimaryKey val bookNumber: String,
    val hadithEndNumber: Int,
    val hadithStartNumber: Int,
    val numberOfHadith: Int
){
    fun getProperHadithBookNameEnglish(): String {
        return if (book.isNotEmpty()) {
            book[0].name
        } else ""
    }
    fun getProperHadithBookNameArabic(): String {
        return if (book.isNotEmpty()) {
            book[1].name
        } else ""
    }
}