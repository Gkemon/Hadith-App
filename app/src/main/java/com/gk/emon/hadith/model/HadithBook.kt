package com.gk.emon.hadith.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import javax.annotation.Nullable

@Entity(
    tableName = "hadithBook",
    primaryKeys = [HadithBook.COLLECTION_NAME_KEY, HadithBook.BOOK_NUMBER_KEY]
)

data class HadithBook(
    @ColumnInfo(name = "book") var book: List<HadithBookMeta> = emptyList(),
    @ColumnInfo(name = BOOK_NUMBER_KEY) val bookNumber: String,
    @ColumnInfo(name = COLLECTION_NAME_KEY) var collectionName: String ="",
    @ColumnInfo(name = "hadithEndNumber") var hadithEndNumber: Int = 0,
    @ColumnInfo(name = "hadithStartNumber") var hadithStartNumber: Int = 0,
    @ColumnInfo(name = "numberOfHadith") var numberOfHadith: Int = 0
) {
    companion object {
        //"const" for compile time and "val" for run time.
        const val BOOK_NUMBER_KEY = "bookNumber"
        const val COLLECTION_NAME_KEY = "collectionName"
    }

    fun getProperHadithBookNameEnglish(): String {
        return if (book.isNotEmpty()) {
            book[0].name
        } else ""
    }

    fun getCompositePrimaryKey(): Pair<String?, String> {
        return Pair(collectionName, bookNumber)
    }

    fun getProperHadithBookNameArabic(): String {
        return if (book.isNotEmpty()) {
            book[1].name
        } else ""
    }
}
