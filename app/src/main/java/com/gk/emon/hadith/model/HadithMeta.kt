package com.gk.emon.hadith.model

data class HadithMeta(
    val body: String,
    val chapterNumber: String,
    val chapterTitle: String,
    val grade: String,
    val lang: String,
    val urn: Int
)