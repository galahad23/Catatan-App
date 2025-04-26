package com.rfgalahad.testcatatan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catatan_table")
data class Catatan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: Double,
    val category: String,
    val date: String
)
