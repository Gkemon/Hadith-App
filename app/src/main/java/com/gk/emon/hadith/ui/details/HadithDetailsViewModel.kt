package com.gk.emon.hadith.ui.details

import androidx.lifecycle.*
import com.gk.emon.core_features.extensions.Event
import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.R
import com.gk.emon.hadith.domain.GetHadithCollections
import com.gk.emon.hadith.model.HadithCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithDetailsViewModel @Inject constructor(
    private val getHadithCollections: GetHadithCollections
) : ViewModel() {

    private val _items = MutableLiveData<List<HadithCollection>>().apply { value = emptyList() }
    val collections: LiveData<List<HadithCollection>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading
    private val _noHadithCollectionLabel = MutableLiveData<Int>()
    val noHadithCollectionLabel: LiveData<Int> = _noHadithCollectionLabel
    private val _noHadithCollectionIconRes = MutableLiveData<Int>()
    val noHadithCollectionIconRes: LiveData<Int> = _noHadithCollectionIconRes

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // Not used at the moment
    private val isDataLoadingError = MutableLiveData<Boolean>()


    private val _openHadithCollectionEvent = MutableLiveData<Event<HadithCollection>>()
    val openHadithCollectionEvent: LiveData<Event<HadithCollection>> = _openHadithCollectionEvent

    /**
     * Called by Data Binding.
     */
    fun openHadithCollection(hadithCollection: HadithCollection) {
        _openHadithCollectionEvent.value = Event(hadithCollection)
    }


    fun loadCollections(forceUpdate: Boolean) {
        _dataLoading.value = true

            viewModelScope.launch {
                val collectionResult = getHadithCollections(forceUpdate)
                if (collectionResult is Result.Success) {
                    isDataLoadingError.value = false
                    _items.value = collectionResult.data
                } else {
                    isDataLoadingError.value = false
                    _items.value = emptyList()
                    showSnackbarMessage(R.string.loading_collection_error)
                }

                _noHadithCollectionLabel.value
                _dataLoading.value = false
            }

    }
    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

}