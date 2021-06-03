package com.gk.emon.hadith.ui.details

import androidx.lifecycle.*
import com.gk.emon.core_features.extensions.Event
import com.gk.emon.core_features.functional.Result
import com.gk.emon.hadith.R
import com.gk.emon.hadith.domain.GetDetails
import com.gk.emon.hadith.domain.GetHadithCollections
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithDetailsViewModel @Inject constructor(
    private val getHadithDetails: GetDetails
) : ViewModel() {

    var hadith = MutableLiveData<Hadith>()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    // Not used at the moment
    private val isDataLoadingError = MutableLiveData<Boolean>()



    fun loadHadithDetails(forceUpdate: Boolean,collectionName:String,hadithNumber:String) {
        _dataLoading.value = true

            viewModelScope.launch {
                val hadithDetailsResult = getHadithDetails(forceUpdate,collectionName,hadithNumber)
                if (hadithDetailsResult is Result.Success) {
                    isDataLoadingError.value = false
                    hadith.value = hadithDetailsResult.data
                } else {
                    showSnackbarMessage(R.string.loading_collection_error)
                }

                _dataLoading.value = false
            }

    }
    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

}