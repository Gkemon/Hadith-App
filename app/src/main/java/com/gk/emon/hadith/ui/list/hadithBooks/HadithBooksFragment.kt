package com.gk.emon.hadith.ui.list.hadithBooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gk.emon.core_features.base_framework_ui.BaseFragment
import com.gk.emon.core_features.extensions.*
import com.gk.emon.hadith.R
import com.gk.emon.hadith.databinding.FragmentBooksBinding
import com.gk.emon.hadith.ui.list.hadithCollections.HadithCollectionAdapter
import com.gk.emon.hadith.ui.list.hadithCollections.HadithCollectionsFragment
import com.gk.emon.hadith.ui.list.hadithCollections.HadithCollectionsFragmentDirections
import com.gk.emon.hadith.ui.list.hadiths.HadithsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HadithBooksFragment : BaseFragment() {
    private val viewModelHadithBooks: HadithBooksViewModel by viewModels()
    private lateinit var hadithBooksAdapter: HadithBooksAdapter
    private lateinit var viewDataBinding: FragmentBooksBinding
    private val args: HadithBooksFragmentArgs by navArgs()

    companion object {
        fun newInstance() =
            HadithCollectionsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentBooksBinding.inflate(inflater, container, false).apply {
            viewModel = viewModelHadithBooks
            lifecycleOwner = this@HadithBooksFragment.viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        setupLoading()
        setupNavigation()
        viewModelHadithBooks.loadBooks(true, args.collectionName)
    }

    private fun setupLoading() {
        viewModelHadithBooks.dataLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                viewDataBinding.tvEmpty.invisible()
                showLoadingPopup(activity)
            } else {
                hideLoadingPopup(activity)
            }
        })
    }

    private fun setupNavigation() {
        viewModelHadithBooks.openHadithBookEvent.observe(this.viewLifecycleOwner, EventObserver {
            findNavController().navigate(
                HadithBooksFragmentDirections.actionBooksToHadiths(
                    args.collectionName,
                    it.bookNumber,
                    it.getProperHadithBookNameEnglish()
                )
            )
        })
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            hadithBooksAdapter = HadithBooksAdapter(R.layout.item_hadith_books, viewModel)
            viewDataBinding.rvBooksList.adapter = hadithBooksAdapter
            viewModelHadithBooks.books.observe(this.viewLifecycleOwner, Observer {
                if (it.isEmpty()) viewDataBinding.tvEmpty.visible()
                else viewDataBinding.tvEmpty.invisible()
                hadithBooksAdapter.setList(it)
            })
        }


    }

}