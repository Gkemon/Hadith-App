package com.gk.emon.hadith.domain

import com.gk.emon.hadith.data.repository.HadithRepositoryNavigation
import com.gk.emon.hadith.model.HadithCollection
import  com.gk.emon.core_features.functional.Result


class GetHadithCollections(private val hadithRepositoryNavigation: HadithRepositoryNavigation) {
    suspend operator fun invoke(
        forceUpdate: Boolean = false
    ): Result<List<HadithCollection>> {

        val hadithCollections = hadithRepositoryNavigation.getHadithCollections(forceUpdate)

        if (hadithCollections is Result.Success) {
            return hadithCollections
        }
        return hadithCollections

    }

}