package com.gk.emon.hadith.ui.list.hadithCollections

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.gk.emon.core_features.extensions.Event
import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.R
import com.gk.emon.hadith.domain.GetHadithCollections
import com.gk.emon.hadith.model.HadithCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithCollectionsViewModel @Inject constructor(
    private val getHadithCollections: GetHadithCollections
) : ViewModel() {
    private val _items =
        MutableLiveData<List<HadithCollection>>().apply { value = emptyList() }

    val collections: LiveData<List<HadithCollection>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    val showEmpty: LiveData<Boolean> = Transformations.map(_items) { it.isEmpty() }

    @Inject
    lateinit var context: Application
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText


    private val _openHadithCollectionEvent = MutableLiveData<Event<HadithCollection>>()
    val openHadithCollectionEvent: LiveData<Event<HadithCollection>> = _openHadithCollectionEvent


    fun openHadithCollection(hadithCollection: HadithCollection) {
        _openHadithCollectionEvent.value = Event(hadithCollection)
    }


    fun loadCollections(forceUpdate: Boolean) {
        _dataLoading.value = true
        viewModelScope.launch {
            when (val collectionResult = getHadithCollections(forceUpdate)) {
                is Result.Error -> {
                    _items.value = emptyList()
                    collectionResult.failure.getProperError(context).let { showSnackbarMessage(it) }
                }
                is Result.Success -> {
                    _items.value = collectionResult.data
                }
            }


            _dataLoading.value = false
        }

    }

    private fun showSnackbarMessage(message: String) {
        _snackbarText.value = Event(message)
    }

}