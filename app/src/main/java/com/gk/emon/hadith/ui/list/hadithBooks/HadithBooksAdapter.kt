package com.gk.emon.hadith.ui.list.hadithBooks

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.gk.emon.hadith.databinding.ItemHadithBooksBinding
import com.gk.emon.hadith.model.HadithBook

/**
 * Created by Gk Emon on 10/9/2020.
 */
class HadithBooksAdapter(
    @LayoutRes layoutResId: Int,
    val viewModel: HadithBooksViewModel
) : BaseQuickAdapter<HadithBook, BaseDataBindingHolder<ItemHadithBooksBinding>>(
    layoutResId
) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemHadithBooksBinding>,
        item: HadithBook
    ) {
        holder.dataBinding?.hadithBook = item
        holder.dataBinding?.viewModel = viewModel
    }
}