package com.gk.emon.hadith.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gk.emon.core_features.base_framework_ui.BaseFragment
import com.gk.emon.core_features.extensions.hideLoadingPopup
import com.gk.emon.core_features.extensions.invisible
import com.gk.emon.core_features.extensions.showLoadingPopup
import com.gk.emon.hadith.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HadithsDetailsFragment : BaseFragment() {
    private val viewModelHadithDetails: HadithDetailsViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentDetailsBinding

    companion object {
        fun newInstance() =
            HadithsDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            viewModel = viewModelHadithDetails
            lifecycleOwner = this@HadithsDetailsFragment.viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoading()
        viewModelHadithDetails.loadCollections(true)
    }

    private fun setupLoading() {
        viewModelHadithDetails.dataLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                viewDataBinding.tvEmpty.invisible()
                showLoadingPopup(activity)
            } else {
                hideLoadingPopup(activity)
            }
        })
    }
}