package com.gk.emon.hadith.model

data class Hadith(
    val bookNumber: String,
    val chapterId: String,
    val collection: String,
    val hadith: List<HadithMeta>,
    val hadithNumber: String
)