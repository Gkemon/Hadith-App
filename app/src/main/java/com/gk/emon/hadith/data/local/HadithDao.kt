package com.gk.emon.hadith.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gk.emon.hadith.model.HadithBook
import com.gk.emon.hadith.model.HadithCollection


@Dao
interface HadithDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCollections(hadithCollections: List<HadithCollection>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBooks(hadithBook: List<HadithBook>)

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
