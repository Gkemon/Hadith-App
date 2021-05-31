package com.gk.emon.hadith.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(
    entities = [Hadith::class, HadithCollection::class, HadithBook::class],
    version = 1, exportSchema = false
)
abstract class HadithRoomDatabase : RoomDatabase() {
    abstract fun hadithDao(): HadithDao
}
