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
)