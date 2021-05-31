package com.gk.emon.hadith.ui.list.hadithCollections

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.gk.emon.hadith.databinding.ItemHadithCollectionsBinding
import com.gk.emon.hadith.model.HadithCollection

/**
 * Created by Gk Emon on 10/9/2020.
 */
class HadithCollectionAdapter(@LayoutRes layoutResId: Int)
                      : BaseQuickAdapter<HadithCollection, BaseDataBindingHolder<ItemHadithCollectionsBinding>>(layoutResId) {
    override fun convert(holder: BaseDataBindingHolder<ItemHadithCollectionsBinding>, item: HadithCollection) {
        holder.dataBinding?.hadithCollection = item
    }
}