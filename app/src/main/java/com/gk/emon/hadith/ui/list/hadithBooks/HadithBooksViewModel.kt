package com.gk.emon.hadith.ui.list.hadithBooks

import android.app.Application
import androidx.lifecycle.*
import com.gk.emon.core_features.extensions.Event
import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.domain.GetHadithBooks
import com.gk.emon.hadith.model.HadithBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithBooksViewModel @Inject constructor(
    private val getHadithBooks: GetHadithBooks
) : ViewModel() {

    @Inject
    lateinit var context: Application
    private val _items = MutableLiveData<List<HadithBook>>().apply { value = emptyList() }
    val books: LiveData<List<HadithBook>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading
    private val _noHadithBookLabel = MutableLiveData<Int>()

    // This LiveData depends on another so we can use a transformation.
    val showEmpty: LiveData<Boolean> = Transformations.map(_items) {it.isEmpty() }

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    // Not used at the moment
    private val isDataLoadingError = MutableLiveData<Boolean>()


    private val _openHadithBookEvent = MutableLiveData<Event<HadithBook>>()
    val openHadithBookEvent: LiveData<Event<HadithBook>> = _openHadithBookEvent

    /**
     * Called by Data Binding.
     */
    fun openHadithBook(hadithBook: HadithBook) {
        _openHadithBookEvent.value = Event(hadithBook)
    }


    fun loadBooks(forceUpdate: Boolean, collectionName: String) {
        _dataLoading.value = true

        viewModelScope.launch {
            val collectionResult = getHadithBooks(forceUpdate, collectionName)
            if (collectionResult is Result.Success) {
                isDataLoadingError.value = false
                _items.value = collectionResult.data
            } else {
                isDataLoadingError.value = false
                _items.value = emptyList()
                showSnackbarMessage(
                    (collectionResult as Result.Error).failure.getProperError(
                        context
                    )
                )
            }

            _noHadithBookLabel.value
            _dataLoading.value = false
        }

    }

    private fun showSnackbarMessage(message: String) {
        _snackbarText.value = Event(message)
    }

}