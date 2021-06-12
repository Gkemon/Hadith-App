package com.gk.emon.hadith.data.local

import androidx.room.*
import com.gk.emon.hadith.model.Hadith
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection


@Dao
interface HadithDao {
    /*Do update because if items are exist in local db then it simply update it otherwise
    * insert it. If we annotated it as "Insert" then it will redundantly insert it */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCollections(hadithCollections: List<HadithCollection>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBooks(hadithBook: List<HadithBook>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHadiths(hadiths: List<Hadith>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHadith(hadith: Hadith)

    @Query("SELECT * FROM HadithCollection")
    suspend fun getCollections(): List<HadithCollection>

    @Query("SELECT * FROM HadithBook WHERE collectionName = :collection")
    suspend fun getBooks(collection: String): List<HadithBook>

    @Query("SELECT * FROM Hadith WHERE hadithNumber = :hadithNo")
    suspend fun getHadith(hadithNo: String): Hadith

    @Query("SELECT * FROM Hadith WHERE collection = :collectionName AND bookNumber = :bookName")
    suspend fun getHadiths(collectionName: String,bookName:String): List<Hadith>

    /*   @Query("SELECT * FROM Tasks")
       fun observeTasks(): LiveData<List<Task>>
       @Query("SELECT * FROM Tasks WHERE entryid = :taskId")
       fun observeTaskById(taskId: String): LiveData<Task>

       @Query("SELECT * FROM Tasks")
       suspend fun getTasks(): List<Task>
       @Query("SELECT * FROM Tasks WHERE entryid = :taskId")
       suspend fun getTaskById(taskId: String): Task?

       @Insert(onConflict = OnConflictStrategy.REPLACE)
       suspend fun insertTask(task: Task)
       @Update
       suspend fun updateTask(task: Task): Int

       @Query("UPDATE tasks SET completed = :completed WHERE entryid = :taskId")
       suspend fun updateCompleted(taskId: String, completed: Boolean)

       */
    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     *//*
    @Query("DELETE FROM Tasks WHERE entryid = :taskId")
    suspend fun deleteTaskById(taskId: String): Int

    */
    /**
     * Delete all tasks.
     *//*
    @Query("DELETE FROM Tasks")
    suspend fun deleteTasks()

    */
    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     *//*
    @Query("DELETE FROM Tasks WHERE completed = 1")
    suspend fun deleteCompletedTasks(): Int*/
}
