package com.rfgalahad.testcatatan.data.repository

import com.rfgalahad.testcatatan.data.local.Catatan
import com.rfgalahad.testcatatan.data.local.CatatanDao
import kotlinx.coroutines.flow.Flow

class CatatanRepository(private val dao: CatatanDao) {

    val allCatatan: Flow<List<Catatan>> = dao.getAllCatatan()

    suspend fun insert(catatan: Catatan) = dao.insert(catatan)
    suspend fun update(catatan: Catatan) = dao.update(catatan)
    suspend fun getCatatanById(id: Int) = dao.getCatatanById(id)
    suspend fun delete(catatan: Catatan) = dao.delete(catatan)
}