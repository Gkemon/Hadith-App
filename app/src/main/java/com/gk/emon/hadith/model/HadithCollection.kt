package com.gk.emon.hadith.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hadithCollection")
data class HadithCollection(
    val collection: List<CollectionMeta>,
    val hasBooks: Boolean,
    val hasChapters: Boolean,
    @PrimaryKey val name: String,
    val totalAvailableHadith: Int,
    val totalHadith: Int
)