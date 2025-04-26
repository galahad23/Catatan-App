package com.rfgalahad.testcatatan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rfgalahad.testcatatan.data.local.Catatan
import com.rfgalahad.testcatatan.data.local.CatatanDatabase
import com.rfgalahad.testcatatan.data.repository.CatatanRepository
import kotlinx.coroutines.launch

class CatatanViewModel(application: Application): AndroidViewModel(application) {

    private val repository: CatatanRepository
    val allCatatan: LiveData<List<Catatan>>

    init {
        val dao = CatatanDatabase.getDatabase(application).catatanDao()
        repository = CatatanRepository(dao)
        allCatatan = repository.allCatatan.asLiveData()
    }

    fun insert(catatan: Catatan) = viewModelScope.launch {
        repository.insert(catatan)
    }

    fun update(catatan: Catatan) = viewModelScope.launch {
        repository.update(catatan)
    }

    suspend fun getCatatanById(id: Int): Catatan {
        return repository.getCatatanById(id)
    }

    fun delete(catatan: Catatan) = viewModelScope.launch {
        repository.delete(catatan)
    }
}