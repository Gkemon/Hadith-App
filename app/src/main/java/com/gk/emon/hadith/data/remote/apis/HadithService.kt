/**
 * Copyright (C) 2020 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
