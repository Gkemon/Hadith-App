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

import com.gk.emon.hadith.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface HadithApis {

    companion object {
        const val api_key_label = "X-API-Key"
        const val api_key = "SqD712P3E82xnwOAEOkGd5JZH8s9wRR24TqNFzjk"
        private const val v1 = "v1/"
        const val baseURL = "https://api.sunnah.com/$v1"
        private const val collectionName = "collectionName"
        private const val hadithNumber = "hadithNumber"
        private const val bookNumber = "bookNumber"
    }

    @GET("collections")
    fun collections(): Call<HadithCollectionResponse>

    @GET("collections/{$collectionName}/books")
    fun books(@Path(collectionName) collectionNameValue: String): Call<HadithBooksResponse>


    @GET("collections/{$collectionName}/hadiths/{$hadithNumber}")
    fun hadith(
        @Path(collectionName) collectionNameValue: String,
        @Path(hadithNumber) hadithNumberValue: String
    ): Call<HadithDetailsResponse>

    @GET("collections/{$collectionName}/books/{$bookNumber}/hadiths")
    fun hadithsOfBook(
        @Path(collectionName) collectionNameValue: String,
        @Path(bookNumber) bookNumberValue: String,
        @Query("limit") limit: Int, @Query("page") page: Int
    ): Call<HadithListResponse>


}
