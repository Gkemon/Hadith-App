package com.gk.emon.hadith.domain

import com.gk.emon.hadith.data.repository.HadithRepositoryNavigation
import com.gk.emon.hadith.model.HadithCollection
import  com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.model.HadithBook
import javax.inject.Inject


class GetHadithBooks @Inject constructor(private val hadithRepositoryNavigation: HadithRepositoryNavigation) {
    suspend operator fun invoke(
        forceUpdate: Boolean = false, collectionName: String
    ): Result<List<HadithBook>> {

        val hadithCollections = hadithRepositoryNavigation.getHadithBooks(forceUpdate,collectionName)

        if (hadithCollections is Result.Success) {
            return hadithCollections
        }
        return hadithCollections

    }

}