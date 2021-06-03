package com.gk.emon.hadith.model

data class HadithBooksResponse(
    val data: List<HadithBook>,
    val limit: Int,
    val next: Int,
    val previous: Any,
    val total: Int
)