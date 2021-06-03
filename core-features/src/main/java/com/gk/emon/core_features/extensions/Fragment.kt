package com.gk.emon.core_features.extensions

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.gk.emon.core_features.base_framework_ui.BaseActivity
import com.gk.emon.core_features.base_framework_ui.BaseFragment
import com.gk.emon.lovelyLoading.LoadingPopup

fun BaseFragment.close() = fragmentManager?.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).getViewContainer()

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun showLoadingPopup(activity: FragmentActivity?){
    activity?.let { it -> LoadingPopup.showLoadingPopUp(it) }
}

fun hideLoadingPopup(activity: FragmentActivity?){
    activity?.let { it -> LoadingPopup.hideLoadingPopUp(it) }
}