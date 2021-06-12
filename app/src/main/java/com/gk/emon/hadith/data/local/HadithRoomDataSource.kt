package com.gk.emon.hadith.data.local

import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.data.HadithDataSource
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HadithRoomDataSource @Inject constructor(
    private val hadithDao: HadithDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HadithDataSource {
    override suspend fun getHadithCollections(): Result<List<HadithCollection>> =
        withContext(ioDispatcher) {
            Result.Success(hadithDao.getCollections())
        }


    override suspend fun getHadithBooks(collectionName: String): Result<List<HadithBook>> =
        withContext(ioDispatcher) {
            Result.Success(hadithDao.getBooks(collectionName))
        }


    override suspend fun getHadiths(
        collectionName: String,
        bookNumber: String
    ): Result<List<Hadith>> =
        withContext(ioDispatcher) {
            Result.Success(hadithDao.getHadiths(collectionName, bookNumber))
        }


    override suspend fun getHadith(collectionName: String, hadithNumber: String): Result<Hadith> =
        withContext(ioDispatcher) {
            Result.Success(hadithDao.getHadith(collectionName))
        }


    override suspend fun saveHadithCollections(hadithCollections: List<HadithCollection>) =
        withContext(ioDispatcher) {
            hadithDao.saveCollections(hadithCollections)
        }

    override suspend fun saveHadithCollection(hadithCollection: HadithCollection) {
        TODO("Not yet implemented")
    }

    override suspend fun saveHadithBooks(hadithBooks: List<HadithBook>) =
        withContext(ioDispatcher) {
            hadithDao.saveBooks(hadithBooks)
        }

    override suspend fun saveHadithBook(hadithBook: HadithBook) {
        TODO("Not yet implemented")
    }

    override suspend fun saveHadiths(hadiths: List<Hadith>) =
        withContext(ioDispatcher) {
            hadithDao.saveHadiths(hadiths)
        }

    override suspend fun saveHadith(hadith: Hadith) =
        withContext(ioDispatcher) {
            hadithDao.saveHadith(hadith)
        }
}