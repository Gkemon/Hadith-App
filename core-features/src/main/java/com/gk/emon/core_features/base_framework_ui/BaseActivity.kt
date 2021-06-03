package com.gk.emon.core_features.base_framework_ui

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
//For future maintainability
abstract class BaseActivity : AppCompatActivity(){
    abstract fun getViewContainer(): View
}