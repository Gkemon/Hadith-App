package com.gk.emon.hadith.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hadith")
data class Hadith(
    val bookNumber: String="",
    val chapterId: String,
    var collection: String,
    val hadith: List<HadithMeta>,
    @PrimaryKey val hadithNumber: String
) {
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