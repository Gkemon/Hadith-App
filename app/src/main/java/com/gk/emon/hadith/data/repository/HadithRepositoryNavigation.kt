package com.gk.emon.hadith.data.repository

import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection

interface HadithRepositoryNavigation {
    suspend fun getHadithCollections(forceUpdate: Boolean): Result<List<HadithCollection>>
    suspend fun getHadithBooks(forceUpdate: Boolean,collectionName: String): Result<List<HadithBook>>
    suspend fun getHadiths(forceUpdate: Boolean,collectionName: String, bookNumber: String): Result<List<Hadith>>
    suspend fun getHadith(forceUpdate: Boolean,collectionName: String, hadithNumber: String): Result<Hadith>
}
