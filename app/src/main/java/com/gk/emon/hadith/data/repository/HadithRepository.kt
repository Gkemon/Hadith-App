package com.gk.emon.hadith.data.repository

import com.gk.emon.core_features.exceptions.Failure
import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.R
import com.gk.emon.hadith.appFailure.AppFeatureFailures
import com.gk.emon.hadith.data.HadithDataSource
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class HadithRepository @Inject constructor(
    private val hadithDataSourceRemote: HadithDataSource,
    private val hadithDataSourceLocal: HadithDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HadithRepositoryNavigation {
    private var cachedCollections: ConcurrentMap<String, HadithCollection>? = null
    private var cachedBooks: ConcurrentMap<Long, HadithBook>? = null
    private var cachedHadiths: ConcurrentMap<String, Hadith>? = null


    override suspend fun getHadithCollections(forceUpdate: Boolean): Result<List<HadithCollection>> {

        return withContext(ioDispatcher) {
            //First check that if it is persisted in memory (ram)
            if (!forceUpdate) {
                cachedCollections?.let { cachedTasks ->
                    return@withContext Result.Success(cachedTasks.values.sortedBy { it.name })
                }
            }

            val hadithCollections = fetchHadithCollectionsFromRemoteOrLocal(forceUpdate)
            // Refresh the cache with the hadith collections
            (hadithCollections as? Result.Success)?.data?.forEach { cacheHadithCollection(it) }

            cachedCollections?.values?.let { tasks ->
                return@withContext Result.Success(tasks.sortedBy { it.name })
            }

            (hadithCollections as? Result.Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(AppFeatureFailures.CollectionListNotAvailable.apply {
                messageStringRes = R.string.no_hadith_collection_available
            })
        }

    }

    private suspend fun refreshLocalHadithCollections(collections: List<HadithCollection>) {
        hadithDataSourceLocal.saveHadithCollections(collections)
    }

    private suspend fun refreshLocalHadithBooks(hadithBooks: List<HadithBook>) {
        hadithDataSourceLocal.saveHadithBooks(hadithBooks)
    }

    private suspend fun refreshLocalHadiths(hadiths: List<Hadith>, collectionName: String) {
        hadiths.map { it.collection = collectionName }
        hadithDataSourceLocal.saveHadiths(hadiths)
    }

    private suspend fun refreshLocalHadithDetails(hadith: Hadith, collectionName: String) {
        hadith.collection = collectionName
        hadithDataSourceLocal.saveHadith(hadith)
    }

    private fun cacheHadithCollection(pendingCacheHadithCollection: HadithCollection): HadithCollection {
        // Create if it doesn't exist.
        if (cachedCollections == null) {
            cachedCollections = ConcurrentHashMap()
        }
        cachedCollections?.put(pendingCacheHadithCollection.name, pendingCacheHadithCollection)
        return pendingCacheHadithCollection
    }

    private fun cacheHadithBooks(pendingCacheHadithBook: HadithBook): HadithBook {
        // Create if it doesn't exist.
        if (cachedBooks == null) {
            cachedBooks = ConcurrentHashMap()
        }
        cachedBooks?.put(pendingCacheHadithBook.id, pendingCacheHadithBook)
        return pendingCacheHadithBook
    }

    private fun cacheHadiths(pendingCacheHadith: Hadith): Hadith {
        // Create if it doesn't exist.
        if (cachedHadiths == null) {
            cachedHadiths = ConcurrentHashMap()
        }
        cachedHadiths?.put(pendingCacheHadith.hadithNumber, pendingCacheHadith)
        return pendingCacheHadith
    }


    private suspend fun fetchHadithCollectionsFromRemoteOrLocal(forceUpdate: Boolean): Result<List<HadithCollection>> {
        // Remote first
        when (val remoteCollection = hadithDataSourceRemote.getHadithCollections()) {
            is Error -> return Result.Error((remoteCollection as Result.Error).failure)
            is Result.Success -> {
                refreshLocalHadithCollections(remoteCollection.data)
                return remoteCollection
            }
        }


        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(Failure.SystemError.apply {
                messageStringRes = R.string.error_in_server_call
            })
        }

        // Local if remote fails
        val localCollections = hadithDataSourceLocal.getHadithCollections()
        if (localCollections is Result.Success) return localCollections

        return Result.Error(AppFeatureFailures.CollectionListNotAvailable.apply {
            message = "Failed to fetch hadith collection both from local and remote data sources"
        })
    }

    private suspend fun fetchBooksFromRemoteOrLocal(
        forceUpdate: Boolean,
        collectionName: String
    ): Result<List<HadithBook>> {
        // Remote first
        when (val remoteBooks = hadithDataSourceRemote.getHadithBooks(collectionName)) {
            is Error -> return Result.Error((remoteBooks as Result.Error).failure)
            is Result.Success -> {
                refreshLocalHadithBooks(remoteBooks.data)
                return remoteBooks
            }
        }


        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(AppFeatureFailures.BookListNotAvailable.apply {
                messageStringRes = R.string.error_in_server_call
            })
        }

        // Local if remote fails
        val localBooks = hadithDataSourceLocal.getHadithBooks(collectionName)
        if (localBooks is Result.Success) return localBooks
        return Result.Error(AppFeatureFailures.BookListNotAvailable.apply {

        })
    }

    private suspend fun fetchHadithsFromRemoteOrLocal(
        forceUpdate: Boolean,
        collectionName: String,
        bookNumber: String
    ): Result<List<Hadith>> {
        // Remote first
        when (val remoteHadiths =
            hadithDataSourceRemote.getHadiths(collectionName, bookNumber)) {
            is Error -> return Result.Error((remoteHadiths as Result.Error).failure)
            is Result.Success -> {
                refreshLocalHadiths(remoteHadiths.data, collectionName)
                return remoteHadiths
            }
        }


        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(AppFeatureFailures.HadithListNotAvailable.apply {
                messageStringRes = R.string.error_in_server_call
            })
        }

        // Local if remote fails
        val localHadiths = hadithDataSourceLocal.getHadiths(collectionName, bookNumber)
        if (localHadiths is Result.Success) return localHadiths
        return Result.Error(AppFeatureFailures.HadithListNotAvailable.apply {
            messageStringRes = R.string.error_in_both_local_and_remote
        })
    }

    private suspend fun fetchHadithDetailsFromRemoteOrLocal(
        forceUpdate: Boolean,
        collectionName: String,
        hadithNumber: String
    ): Result<Hadith> {
        // Remote first
        when (val remoteHadith =
            hadithDataSourceRemote.getHadith(collectionName, hadithNumber)) {
            is Error -> return Result.Error((remoteHadith as Result.Error).failure)
            is Result.Success -> {
                refreshLocalHadithDetails(remoteHadith.data, collectionName)
                return remoteHadith
            }
        }


        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(AppFeatureFailures.HadithNotAvailable.apply {
                messageStringRes = R.string.error_in_server_call
            })
        }

        // Local if remote fails
        val localHadiths = hadithDataSourceLocal.getHadith(collectionName, hadithNumber)
        if (localHadiths is Result.Success) return localHadiths
        return Result.Error(AppFeatureFailures.HadithNotAvailable.apply {
            messageStringRes = R.string.error_in_both_local_and_remote
        })
    }


    override suspend fun getHadithBooks(
        forceUpdate: Boolean,
        collectionName: String
    ): Result<List<HadithBook>> {
        return withContext(ioDispatcher) {

            //First check that if it is persisted in memory (ram)

            if (!forceUpdate) {
                //TODO: memory cached data might have a bug.
                cachedBooks?.let { cachedBooks ->
                    val cachedData =
                        cachedBooks.values.filter { it.collectionName == collectionName }
                            .sortedBy { it.bookNumber }
                    if (cachedData.isNullOrEmpty())
                        return@withContext Result.Success(cachedData)
                }
            }

            val hadithBooks = fetchBooksFromRemoteOrLocal(forceUpdate, collectionName)
            // Refresh the cache with the hadith books
            (hadithBooks as? Result.Success)?.data?.forEach { cacheHadithBooks(it) }

            (hadithBooks as? Result.Success)?.let {
                if (it.data.isNotEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(AppFeatureFailures.HadithBookListAvailable.apply {
                message = "No hadith books found"
            })
        }
    }

    override suspend fun getHadiths(
        forceUpdate: Boolean,
        collectionName: String,
        bookNumber: String
    ): Result<List<Hadith>> {
        return withContext(ioDispatcher) {
            //First check that if it is persisted in memory (ram)
            if (!forceUpdate) {
                cachedHadiths?.let { cachedHadiths ->
                    return@withContext Result.Success(cachedHadiths.values.sortedBy { it.hadithNumber })
                }
            }

            val hadiths = fetchHadithsFromRemoteOrLocal(forceUpdate, collectionName, bookNumber)
            // Refresh the cache with the hadith collections
            (hadiths as? Result.Success)?.data?.forEach { cacheHadiths(it) }

            //Soring
            /* cachedHadiths?.values?.let { hadiths ->
                 return@withContext Result.Success(hadiths.sortedBy { it.bookNumber })
             }*/

            (hadiths as? Result.Success)?.let {
                if (it.data.isNotEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(AppFeatureFailures.HadithListNotAvailable.apply {
                messageStringRes = R.string.no_hadith_available
            })
        }
    }

    override suspend fun getHadith(
        forceUpdate: Boolean,
        collectionName: String,
        hadithNumber: String
    ): Result<Hadith> {
        return withContext(ioDispatcher) {
            //First check that if it is persisted in memory (ram)
            /* if (!forceUpdate) {
                 cachedHadiths?.let { cachedHadiths ->
                     return@withContext Result.Success(cachedHadiths.values.sortedBy { it.hadithNumber })
                 }
             }*/

            val hadiths =
                fetchHadithDetailsFromRemoteOrLocal(forceUpdate, collectionName, hadithNumber)

            //TODO: Do in memory cache lated
            //Soring
            /* cachedHadiths?.values?.let { hadiths ->
                 return@withContext Result.Success(hadiths.sortedBy { it.bookNumber })
             }*/

            (hadiths as? Result.Success)?.let {
                return@withContext Result.Success(it.data)
            }
            return@withContext Result.Error(AppFeatureFailures.HadithNotAvailable.apply {
                messageStringRes = R.string.no_hadith_available
            })
        }
    }
}