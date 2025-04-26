package com.rfgalahad.testcatatan.ui.screen

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rfgalahad.testcatatan.data.local.Catatan
import java.util.Calendar

@Composable
fun UbahCatatanScreen(
    original: Catatan,
    onUpdate: (Catatan) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var name by remember { mutableStateOf(original.name) }
    var amount by remember { mutableStateOf(original.amount.toString()) }
    var category by remember { mutableStateOf(original.category) }
    var date by remember { mutableStateOf(original.date) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            date = "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
        },
        year, month, day
    )

    val isFormValid = name.isNotBlank() && amount.isNotBlank() && category.isNotBlank() && date.isNotBlank()

    Column(Modifier.padding(16.dp)) {
        Text("Ubah Catatan", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama") })
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Jumlah") },
            leadingIcon = { Text("Rp", modifier = Modifier.padding(start = 8.dp)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Kategori") })
        OutlinedTextField(
            value = date,
            onValueChange = {},
            label = { Text("Tanggal") },
            readOnly = true,
            modifier = Modifier.clickable { datePickerDialog.show() }
        )
        Row(Modifier.padding(top = 16.dp)) {
            Button(onClick = {
                if (isFormValid) {
                    val updated = original.copy(
                        name = name,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        category = category,
                        date = date
                    )
                    onUpdate(updated)
                } else {
                    Toast.makeText(context, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Simpan Perubahan")
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = onCancel) {
                Text("Batal")
            }
        }
    }
}
