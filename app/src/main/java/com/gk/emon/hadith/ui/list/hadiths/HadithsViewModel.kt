package com.gk.emon.hadith.ui.list.hadiths

import androidx.lifecycle.*
import com.gk.emon.core_features.extensions.Event
import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.R
import com.gk.emon.hadith.domain.GetHadiths
import com.gk.emon.hadith.model.Hadith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithsViewModel @Inject constructor(
    private val getHadiths : GetHadiths
) : ViewModel() {

    private val _items = MutableLiveData<List<Hadith>>().apply { value = emptyList() }
    val hadiths: LiveData<List<Hadith>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading
    private val _noHadithLabel = MutableLiveData<Int>()
    val noHadithLabel: LiveData<Int> = _noHadithLabel
    private val _noHadithIconRes = MutableLiveData<Int>()
    val noHadithIconRes: LiveData<Int> = _noHadithIconRes

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // Not used at the moment
    private val isDataLoadingError = MutableLiveData<Boolean>()


    private val _openHadithEvent = MutableLiveData<Event<Hadith>>()
    val openHadithEvent: LiveData<Event<Hadith>> = _openHadithEvent

    /**
     * Called by Data Binding.
     */
    fun openHadith(Hadith: Hadith) {
        _openHadithEvent.value = Event(Hadith)
    }


    fun loadHadiths(forceUpdate: Boolean,collectionName:String,books:String) {
        _dataLoading.value = true

            viewModelScope.launch {
                val hadithResult = getHadiths(forceUpdate,collectionName,books)
                if (hadithResult is Result.Success) {
                    isDataLoadingError.value = false
                    _items.value = hadithResult.data
                } else {
                    isDataLoadingError.value = false
                    _items.value = emptyList()
                    showSnackbarMessage(R.string.loading_collection_error)
                }

                _noHadithLabel.value
                _dataLoading.value = false
            }

    }
    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

}