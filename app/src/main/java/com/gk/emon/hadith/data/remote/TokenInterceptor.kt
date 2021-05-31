package com.gk.emon.hadith.data.remote

import com.gk.emon.hadith.data.local.HadithPreference
import com.gk.emon.hadith.data.remote.apis.HadithApis
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor() : Interceptor {
    @Inject
    lateinit var hadithPreference: HadithPreference
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val token = hadithPreference.getString(HadithApis.api_key_label)
        val url =
            original.url.newBuilder().addQueryParameter(HadithApis.api_key_label, token).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}