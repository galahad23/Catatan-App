package com.rfgalahad.testcatatan.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CatatanDao {

    @Insert
    suspend fun insert(catatan: Catatan)

    @Update
    suspend fun update(catatan: Catatan)

    @Query("SELECT * FROM catatan_table ORDER BY date DESC")
    fun getAllCatatan(): Flow<List<Catatan>>

    @Query("SELECT * FROM catatan_table WHERE id = :id")
    suspend fun getCatatanById(id: Int): Catatan

    @Delete
    suspend fun delete(catatan: Catatan)
}