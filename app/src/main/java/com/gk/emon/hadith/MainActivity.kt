package com.gk.emon.hadith

import com.gk.emon.core_features.base_framework_ui.BaseActivity
import com.gk.emon.hadith.ui.list.hadithCollections.HadithCollectionsFragment

class MainActivity : BaseActivity() {
    override fun fragment() = HadithCollectionsFragment.newInstance()
}