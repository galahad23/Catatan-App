package com.rfgalahad.testcatatan.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rfgalahad.testcatatan.data.local.Catatan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCatatanScreen(
    catatanList: List<Catatan>,
    onAddClick: () -> Unit,
    onUpdateClick: (Catatan) -> Unit,
    onDeleteClick: (Catatan) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<Catatan?>(null) }

    if (showDialog && itemToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menghapus catatan ini?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClick(itemToDelete!!)
                    showDialog = false
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("List Catatan Pengeluaran") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(catatanList) { catatan ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nama: ${catatan.name}")
                        Text("Jumlah: Rp ${catatan.amount}")
                        Text("Kategori: ${catatan.category}")
                        Text("Tanggal: ${catatan.date}")
                        Row {
                            TextButton(onClick = { onUpdateClick(catatan) }) {
                                Text("Ubah")
                            }
                            TextButton(onClick = {
                                itemToDelete = catatan
                                showDialog = true
                            }) {
                                Text("Hapus", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}
