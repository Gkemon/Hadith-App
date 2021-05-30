package com.gk.emon.hadith.data.repository

import com.gk.emon.core_features.functional.Result
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

class HistoryRepository @Inject constructor(
    private val hadithDataSourceRemote: HadithDataSource,
    private val hadithDataSourceLocal: HadithDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HadithRepositoryNavigation {
    private var cachedCollections: ConcurrentMap<String, HadithCollection>? = null

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

            return@withContext Result.Error(Exception("No hadith collection found"))
        }

    }

    private suspend fun refreshLocalHadithCollections(collections: List<HadithCollection>) {
        hadithDataSourceLocal.saveHadithCollections(collections)
    }

    private fun cacheHadithCollection(pendingCacheHadithCollection: HadithCollection): HadithCollection {
        // Create if it doesn't exist.
        if (cachedCollections == null) {
            cachedCollections = ConcurrentHashMap()
        }
        cachedCollections?.put(pendingCacheHadithCollection.name, pendingCacheHadithCollection)
        return pendingCacheHadithCollection
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
            return Result.Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        // Local if remote fails
        val localTasks = hadithDataSourceLocal.getHadithCollections()
        if (localTasks is Result.Success) return localTasks
        return Result.Error(Exception("Error fetching from remote and local"))
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
}