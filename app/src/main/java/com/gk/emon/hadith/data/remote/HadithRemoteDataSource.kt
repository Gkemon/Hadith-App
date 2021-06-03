package com.gk.emon.hadith.data.remote

import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.data.network.NetworkHandler
import com.gk.emon.hadith.data.HadithDataSource
import com.gk.emon.hadith.data.remote.apis.HadithService
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HadithRemoteDataSource @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val service: HadithService
) : HadithDataSource {


    private fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default: R
    ): Result<R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Result.Success(response.body()?.let { transform(it) } ?: default)
                false -> Result.Error(Exception("Server response is not successful"))
            }
        } catch (exception: Throwable) {
            Result.Error(Exception(exception))
        }
    }


    override suspend fun getHadithCollections(): Result<List<HadithCollection>> {
        return when (networkHandler.isNetworkAvailable()) {
            true -> request(
                service.collections(),
                { it.data },
                emptyList()
            )
            false -> Result.Error(Exception("No network found"))
        }
    }

    override suspend fun getHadithBooks(collectionName: String): Result<List<HadithBook>> {
        return when (networkHandler.isNetworkAvailable()) {
            true -> request(
                service.books(collectionName),
                { it.data },
                emptyList()
            )
            false -> Result.Error(Exception("No network found"))
        }
    }

    override suspend fun getHadiths(
        collectionName: String,
        bookNumber: String
    ): Result<List<Hadith>> {
        return when (networkHandler.isNetworkAvailable()) {
            true -> request(
                service.hadithsOfBook(collectionName, bookNumber,10,1),
                { it.data },
                emptyList()
            )
            false -> Result.Error(Exception("No network found"))
        }
    }

    override suspend fun getHadith(collectionName: String, hadithNumber: Int): Result<Hadith> {
        TODO("Not yet implemented")
    }

    override suspend fun saveHadithCollections(hadithCollections: List<HadithCollection>) {
        TODO("Not yet implemented")
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