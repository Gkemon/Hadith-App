package com.gk.emon.hadith.data.local

import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.data.HadithDataSource
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HadithRoomDataSource @Inject constructor(
    private val hadithDao: HadithDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HadithDataSource {
    override suspend fun getHadithCollections(): Result<List<HadithCollection>> {
        TODO("Not yet implemented")
    }

    override suspend fun getHadithBooks(collectionName: String): Result<List<HadithBook>> {
        TODO("Not yet implemented")
    }

    override suspend fun getHadiths(collectionName: String, bookId: Int): Result<List<Hadith>> {
        TODO("Not yet implemented")
    }

    override suspend fun getHadith(collectionName: String, hadithNumber: Int): Result<Hadith> {
        TODO("Not yet implemented")
    }

    override suspend fun saveHadithCollections(hadithCollections: List<HadithCollection>) {
        hadithDao.saveCollections(hadithCollections)
    }

    override suspend fun saveHadithCollection(hadithCollection: HadithCollection) {
        TODO("Not yet implemented")
    }

    override suspend fun saveHadithBooks(hadithBooks: List<HadithBook>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveHadithBook(hadithBook: HadithBook) {
        TODO("Not yet implemented")
    }

    override suspend fun saveHadiths(hadiths: List<Hadith>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveHadith(hadith: Hadith) {
        TODO("Not yet implemented")
    }
}