package com.gk.emon.hadith.data.repository

import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection

interface HadithRepositoryNavigation {
    suspend fun getHadithCollections(forceUpdate: Boolean): Result<List<HadithCollection>>
    suspend fun getHadithBooks(collectionName: String): Result<List<HadithBook>>
    suspend fun getHadiths(collectionName: String, bookId: Int): Result<List<Hadith>>
    suspend fun getHadith(collectionName: String, hadithNumber: Int): Result<Hadith>
}
