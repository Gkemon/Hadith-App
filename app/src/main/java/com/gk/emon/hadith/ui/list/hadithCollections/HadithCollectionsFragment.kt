package com.gk.emon.hadith.ui.list.hadithCollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gk.emon.core_features.base_framework_ui.BaseFragment
import com.gk.emon.hadith.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HadithCollectionsFragment : BaseFragment() {
    private val hadithCollectionsViewModel: HadithCollectionsViewModel by viewModels()

    companion object {
        fun newInstance() =
            HadithCollectionsFragment()
    }

    override fun layoutId(): Int {
       return R.layout.fragment_collections
    }


}