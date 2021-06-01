package com.gk.emon.hadith.ui.list.hadithCollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gk.emon.core_features.base_framework_ui.BaseFragment
import com.gk.emon.core_features.extensions.invisible
import com.gk.emon.core_features.extensions.visible
import com.gk.emon.hadith.R
import com.gk.emon.hadith.databinding.FragmentCollectionsBinding
import com.gk.emon.lovelyLoading.LoadingPopup
import dagger.hilt.android.AndroidEntryPoint

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
            lifecycleOwner = this@HadithCollectionsFragment.viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupLoading()
        hadithCollectionsViewModel.loadCollections(true)
    }

    private fun setupLoading() {
        hadithCollectionsViewModel.dataLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                viewDataBinding.tvEmpty.invisible()
                activity?.let { activity -> LoadingPopup.showLoadingPopUp(activity) }
            }
            else {
                activity?.let { activity -> LoadingPopup.hideLoadingPopUp(activity) }
            }
        })
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            hadithCollectionAdapter = HadithCollectionAdapter(R.layout.item_hadith_collections)
            viewDataBinding.rvMedicineList.adapter = hadithCollectionAdapter
            hadithCollectionsViewModel.collections.observe(this.viewLifecycleOwner, Observer {
                if(it.isEmpty())viewDataBinding.tvEmpty.visible()
                else viewDataBinding.tvEmpty.invisible()
                hadithCollectionAdapter.setList(it)
            })
        }


    }

}