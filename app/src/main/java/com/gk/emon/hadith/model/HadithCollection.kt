package com.gk.emon.hadith.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hadithCollection")
data class HadithCollection(
    val collection: List<HadithCollectionMeta>,
    val hasBooks: Boolean,
    val hasChapters: Boolean,
    @PrimaryKey val name: String,
    val totalAvailableHadith: Int,
    val totalHadith: Int
) {
    fun getProperCollectionEnglishName(): String {
        return if (collection.isNotEmpty()) {
            collection[0].title
        } else name
    }
    fun getProperCollectionArabicName(): String {
        return if (collection.isNotEmpty() && collection.size > 1) {
            collection[1].title
        } else name
    }
}