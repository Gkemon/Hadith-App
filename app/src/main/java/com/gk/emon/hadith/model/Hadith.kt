package com.gk.emon.hadith.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.room.Entity

@Entity(tableName = "hadith", primaryKeys = [Hadith.COLLECTION_NAME_KEY, Hadith.HADITH_NUMBER_KEY])
data class Hadith(
    var bookNumber: String = "",
    val chapterId: String,
    var collection: String,
    val hadith: List<HadithMeta>,
    val hadithNumber: String
) {
    companion object {
        //"const" for compile time and "val" for run time.
        const val HADITH_NUMBER_KEY = "hadithNumber"
        const val COLLECTION_NAME_KEY = "collection"
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun getProperBodyHadithEnglish(): String {
        return if (hadith.isNotEmpty()) {
            android.text.Html.fromHtml(hadith[0].body, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        } else ""
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getProperBodyHadithArabic(): String {
        return if (hadith.isNotEmpty()) {
            android.text.Html.fromHtml(hadith[1].body, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        } else ""
    }
}

