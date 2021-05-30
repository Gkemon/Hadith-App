package com.gk.emon.hadith.model

data class Chapter(
    val bookNumber: String,
    val chapter: List<ChapterMeta>,
    val chapterId: String
)