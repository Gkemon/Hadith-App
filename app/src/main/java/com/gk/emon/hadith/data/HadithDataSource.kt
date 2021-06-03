package com.gk.emon.hadith.data

import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection

interface HadithDataSource {
    suspend fun getHadithCollections(): Result<List<HadithCollection>>
    suspend fun getHadithBooks(collectionName: String): Result<List<HadithBook>>
    suspend fun getHadiths(collectionName: String, bookNumber: String): Result<List<Hadith>>
    suspend fun getHadith(collectionName: String, hadithNumber: Int): Result<Hadith>

    suspend fun saveHadithCollections(hadithCollections: List<HadithCollection>)
    suspend fun saveHadithCollection(hadithCollection: HadithCollection)

    suspend fun saveHadithBooks(hadithBooks: List<HadithBook>)
    suspend fun saveHadithBook(hadithBook: HadithBook)

    suspend fun saveHadiths(hadiths: List<Hadith>)
    suspend fun saveHadith(hadith: Hadith)


}