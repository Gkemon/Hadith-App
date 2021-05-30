package com.gk.emon.hadith.model

data class HadithCollection(
    val collection: List<CollectionMeta>,
    val hasBooks: Boolean,
    val hasChapters: Boolean,
    val name: String,
    val totalAvailableHadith: Int,
    val totalHadith: Int
)