package com.gk.emon.hadith.model

data class HadithCollectionResponse(
    val data: List<HadithCollection>,
    val limit: Int,
    val next: Int,
    val previous: Any,
    val total: Int
)