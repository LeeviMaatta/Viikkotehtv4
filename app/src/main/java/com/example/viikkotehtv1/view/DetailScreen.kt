package com.example.viikkotehtv1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.viikkotehtv1.model.Task

@Composable
fun TaskDetailDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit,
    onDelete: () -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Muokkaa tehtävää") },
        text = {
            Column {
                OutlinedTextField(title, { title = it }, label = { Text("Nimi") })
                OutlinedTextField(description, { description = it }, label = { Text("Kuvaus") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(task.copy(title = title, description = description))
            }) {
                Text("Tallenna")
            }
        },
        dismissButton = {
            Button(onClick = onDelete) {
                Text("Poista")
            }
        }
    )
}