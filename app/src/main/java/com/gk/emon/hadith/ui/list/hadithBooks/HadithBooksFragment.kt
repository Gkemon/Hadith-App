package com.gk.emon.hadith.ui.list.hadithBooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gk.emon.core_features.base_framework_ui.BaseFragment
import com.gk.emon.hadith.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HadithBooksFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            HadithBooksFragment()
    }

    private lateinit var viewModel: HadithBooksViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }

}