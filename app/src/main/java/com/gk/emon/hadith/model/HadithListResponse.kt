package com.gk.emon.hadith.model

data class HadithListResponse(
    val data: List<Hadith>,
    val limit: Int,
    val next: Int,
    val previous: Any,
    val total: Int
)