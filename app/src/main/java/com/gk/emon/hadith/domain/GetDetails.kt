package com.gk.emon.hadith.domain

import com.gk.emon.hadith.data.repository.HadithRepositoryNavigation
import com.gk.emon.hadith.model.HadithCollection
import  com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.model.Hadith
import javax.inject.Inject


class GetDetails @Inject constructor(private val hadithRepositoryNavigation: HadithRepositoryNavigation) {
    suspend operator fun invoke(
        forceUpdate: Boolean = false, collectionName: String, hadithNumber: String
    ): Result<Hadith> {

        val hadith =
            hadithRepositoryNavigation.getHadith(forceUpdate, collectionName, hadithNumber)

        if (hadith is Result.Success) {
            return hadith
        }
        return hadith

    }

}