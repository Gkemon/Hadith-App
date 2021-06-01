package com.gk.emon.hadith.data.remote.apis

import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollectionResponse
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HadithService
@Inject constructor(retrofit: Retrofit) : HadithApis {
    private val hadithApis by lazy { retrofit.create(HadithApis::class.java) }

    override fun collections(): Call<HadithCollectionResponse> = hadithApis.collections()

    override fun books(collectionNameValue: String): Call<HadithBook> =
        hadithApis.books(collectionNameValue)

    override fun hadith(collectionNameValue: String, hadithNumberValue: String): Call<Hadith> =
        hadithApis.hadith(collectionNameValue, hadithNumberValue)

    override fun hadithsOfBook(
        collectionNameValue: String,
        bookNumberValue: String
    ): Call<Hadith> = hadithApis.hadithsOfBook(collectionNameValue, bookNumberValue)
}
