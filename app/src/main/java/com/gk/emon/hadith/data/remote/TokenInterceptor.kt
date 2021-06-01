package com.gk.emon.hadith.data.remote

import com.gk.emon.hadith.data.local.HadithPreference
import com.gk.emon.hadith.data.remote.apis.HadithApis
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(private val hadithPreference: HadithPreference) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = hadithPreference.getString(HadithApis.api_key_label)
        val request = chain
            .request()
            .newBuilder()
            .addHeader(HadithApis.api_key_label, token)
            .build()

        return chain.proceed(request)
    }
}