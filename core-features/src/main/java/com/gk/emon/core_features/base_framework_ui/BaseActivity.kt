package com.gk.emon.core_features.base_framework_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gk.emon.core_features.R
import com.gk.emon.core_features.databinding.ActivityLayoutBinding
import com.gk.emon.core_features.extensions.inTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    lateinit var binding: ActivityLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        setSupportActionBar(binding.includedToolbar.toolbar)
        addFragment(savedInstanceState)
    }
    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(
                R.id.fragmentContainer,
                fragment()
            )
        }
    abstract fun fragment(): BaseFragment
}