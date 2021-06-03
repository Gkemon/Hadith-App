package com.gk.emon.hadith.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import javax.annotation.Nullable

@Entity(tableName = "hadithBook")
data class HadithBook(
    @ColumnInfo(name = "book") var book: List<HadithBookMeta> = emptyList(),
    @ColumnInfo(name = "bookNumber") val bookNumber: String,
    @ColumnInfo(name = "collectionName") var collectionName: String?,
    @ColumnInfo(name = "hadithEndNumber") var hadithEndNumber: Int = 0,
    @ColumnInfo(name = "hadithStartNumber") var hadithStartNumber: Int = 0,
    @ColumnInfo(name = "numberOfHadith") var numberOfHadith: Int = 0,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
) {
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