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
)