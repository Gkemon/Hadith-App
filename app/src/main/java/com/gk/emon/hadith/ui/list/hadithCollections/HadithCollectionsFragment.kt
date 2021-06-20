package com.gk.emon.hadith.ui.list.hadithCollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gk.emon.core_features.base_ui_containers.BaseFragment
import com.gk.emon.core_features.extensions.*
import com.gk.emon.hadith.R
import com.gk.emon.hadith.databinding.FragmentCollectionsBinding
import com.gk.emon.lovelyLoading.LoadingPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HadithCollectionsFragment : BaseFragment() {
    private val viewModelHadithCollections: HadithCollectionsViewModel by viewModels()
    private lateinit var hadithCollectionAdapter: HadithCollectionAdapter
    private lateinit var viewDataBinding: FragmentCollectionsBinding

    companion object {
        fun newInstance() =
            HadithCollectionsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentCollectionsBinding.inflate(inflater, container, false).apply {
            viewModel = viewModelHadithCollections
            lifecycleOwner = this@HadithCollectionsFragment.viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        setupLoading()
        setupToast()
        setupNavigation()
        viewModelHadithCollections.loadCollections(false)
    }

    private fun setupLoading() {
        viewModelHadithCollections.dataLoading.observe(this.viewLifecycleOwner, {
            if (it) {
                viewDataBinding.tvEmpty.invisible()
                LoadingPopup.showLoadingPopUp()
            } else {
                viewDataBinding.tvEmpty.visible()
                LoadingPopup.hideLoadingPopUp()
            }
        })
    }

    private fun setupNavigation() {
        viewModelHadithCollections.openHadithCollectionEvent.observe(
            this.viewLifecycleOwner,
            EventObserver {
                val action = HadithCollectionsFragmentDirections.actionCollectionsToBooks(
                    it.name,
                    it.getProperCollectionEnglishName()
                )
                findNavController().navigate(action)
            })
    }

    private fun setupToast() {
        viewModelHadithCollections.snackbarText.observe(
            this.viewLifecycleOwner,
            EventObserver { Toast.makeText(context, it, Toast.LENGTH_LONG).show()})
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            hadithCollectionAdapter =
                HadithCollectionAdapter(R.layout.item_hadith_collections, viewModel)
            viewDataBinding.rvCollectionList.adapter = hadithCollectionAdapter
            viewModelHadithCollections.collections.observe(this.viewLifecycleOwner, {
                hadithCollectionAdapter.setList(it)
            })
        }
    }

}