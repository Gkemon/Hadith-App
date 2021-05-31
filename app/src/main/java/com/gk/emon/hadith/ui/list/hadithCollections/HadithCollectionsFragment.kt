package com.gk.emon.hadith.ui.list.hadithCollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gk.emon.core_features.base_framework_ui.BaseFragment
import com.gk.emon.hadith.R
import com.gk.emon.hadith.databinding.FragmentCollectionsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HadithCollectionsFragment : BaseFragment() {
    private val hadithCollectionsViewModel: HadithCollectionsViewModel by viewModels()
    private lateinit var hadithCollectionAdapter: HadithCollectionAdapter
    private lateinit var viewDataBinding: FragmentCollectionsBinding

    companion object {
        fun newInstance() =
            HadithCollectionsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCollectionsBinding.inflate(inflater, container, false).apply {
            viewModel = hadithCollectionsViewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        hadithCollectionsViewModel.loadCollections(true)
    }


    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            hadithCollectionAdapter = HadithCollectionAdapter(R.layout.item_hadith_collections)
            viewDataBinding.rvMedicineList.adapter = hadithCollectionAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

}