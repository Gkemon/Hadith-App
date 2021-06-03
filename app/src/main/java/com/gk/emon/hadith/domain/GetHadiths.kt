package com.gk.emon.hadith.domain

import com.gk.emon.hadith.data.repository.HadithRepositoryNavigation
import com.gk.emon.hadith.model.HadithCollection
import  com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.model.Hadith
import javax.inject.Inject


class GetHadiths @Inject constructor(private val hadithRepositoryNavigation: HadithRepositoryNavigation) {
    suspend operator fun invoke(
        forceUpdate: Boolean = false, collectionName: String, bookNumber: String
    ): Result<List<Hadith>> {

        val hadiths =
            hadithRepositoryNavigation.getHadiths(forceUpdate, collectionName, bookNumber)

        if (hadiths is Result.Success) {
            return hadiths
        }
        return hadiths

    }

}