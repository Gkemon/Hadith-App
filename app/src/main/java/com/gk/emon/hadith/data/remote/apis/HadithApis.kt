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
import retrofit2.http.GET
import retrofit2.http.Path

internal interface HadithApis {

    companion object {
        private const val collectionName = "collectionName"
        private const val hadithNumber = "hadithNumber"
        private const val bookNumber = "bookNumber"

    }

    @GET("collections")
    fun collections(): Call<HadithCollectionResponse>

    @GET("collections/{$collectionName}/books")
    fun books(@Path(collectionName) collectionNameValue: String): Call<HadithBook>


    @GET("collections/{$collectionName}/hadiths/{${hadithNumber}}")
    fun hadith(
        @Path(collectionName) collectionNameValue: String,
        @Path(hadithNumber) hadithNumberValue: String
    ): Call<Hadith>

    @GET("collections/{$collectionName}/books/{${bookNumber}}/hadiths")
    fun hadithsOfBook(
        @Path(collectionName) collectionNameValue: String,
        @Path(bookNumber) bookNumberValue: String
    ): Call<Hadith>


}
