package com.gk.emon.core_features.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.gk.emon.core_features.functional.Result

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Result.Error>> LifecycleOwner.failure(liveData: L, body: (Result.Error?) -> Unit) =
    liveData.observe(this, Observer(body))