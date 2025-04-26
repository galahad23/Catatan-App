package com.rfgalahad.testcatatan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Catatan::class], version = 1)
abstract class CatatanDatabase : RoomDatabase() {

    abstract fun catatanDao(): CatatanDao

    companion object {
        @Volatile private var INSTANCE: CatatanDatabase? = null

        fun getDatabase(context: Context): CatatanDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CatatanDatabase::class.java,
                    "catatan_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}