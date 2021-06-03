package com.gk.emon.hadith.ui.list.hadiths

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.gk.emon.hadith.databinding.ItemHadithCollectionsBinding
import com.gk.emon.hadith.databinding.ItemHadithsBinding
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithCollection

/**
 * Created by Gk Emon on 10/9/2020.
 */
class HadithsAdapter(
    @LayoutRes layoutResId: Int,
    val viewModel: HadithsViewModel
) : BaseQuickAdapter<Hadith, BaseDataBindingHolder<ItemHadithsBinding>>(
    layoutResId
) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHadithsBinding>,
        item: Hadith
    ) {
        holder.dataBinding?.hadith = item
        holder.dataBinding?.viewModel = viewModel
    }
}