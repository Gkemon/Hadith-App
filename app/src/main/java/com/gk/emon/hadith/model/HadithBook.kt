package com.gk.emon.hadith.model

data class HadithBook(
    val book: List<HadithBookMeta>,
    val bookNumber: String,
    val hadithEndNumber: Int,
    val hadithStartNumber: Int,
    val numberOfHadith: Int
)