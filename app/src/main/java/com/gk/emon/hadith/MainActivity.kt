package com.gk.emon.hadith

import android.os.Bundle
import com.gk.emon.core_features.base_framework_ui.BaseActivity
import com.gk.emon.hadith.data.local.HadithPreference
import com.gk.emon.hadith.data.remote.apis.HadithApis
import com.gk.emon.hadith.ui.list.hadithCollections.HadithCollectionsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject
    lateinit var hadithPreference: HadithPreference
    //API key will be set dynamically then
    override fun fragment() = HadithCollectionsFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hadithPreference.setString(HadithApis.api_key_label, HadithApis.api_key)
    }
}