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
package com.gk.emon.hadith.di

import com.gk.emon.hadith.BuildConfig
import com.gk.emon.hadith.data.HadithDataSource
import com.gk.emon.hadith.data.local.HadithRoomDataSource
import com.gk.emon.hadith.data.local.HadithRoomDatabase
import com.gk.emon.hadith.data.remote.HadithRemoteDataSource
import com.gk.emon.hadith.data.remote.TokenInterceptor
import com.gk.emon.hadith.data.remote.apis.HadithApis
import com.gk.emon.hadith.data.repository.HadithRepository
import com.gk.emon.hadith.data.repository.HadithRepositoryNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource


    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(HadithApis.baseURL)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            okHttpClientBuilder
                .addInterceptor(loggingInterceptor)
                .addInterceptor(TokenInterceptor())
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @RemoteDataSource
    @Provides
    fun provideRemoteDataSource(): HadithDataSource {
        return HadithRemoteDataSource()
    }

    @Singleton
    @LocalDataSource
    @Provides
    fun provideLocalDataSource(
        database: HadithRoomDatabase,
        ioDispatcher: CoroutineDispatcher
    ): HadithDataSource {
        return HadithRemoteDataSource()
    }


    @Singleton
    @Provides
    fun provideRemoteDataSource(
        remoteDataSource: HadithDataSource,
        localDataSource: HadithDataSource,
        ioDispatcher: CoroutineDispatcher
    ): HadithRepositoryNavigation {
        return HadithRepository(
            remoteDataSource, localDataSource, ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideHadithRepository(
        remoteDataSource: HadithDataSource,
        localDataSource: HadithDataSource,
        ioDispatcher: CoroutineDispatcher
    ): HadithRepositoryNavigation {
        return HadithRepository(
            remoteDataSource, localDataSource, ioDispatcher
        )
    }


}
