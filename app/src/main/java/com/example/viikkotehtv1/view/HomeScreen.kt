package com.example.viikkotehtv1.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.unit.dp
import com.example.viikkotehtv1.ViewModel.TaskViewModel
import com.example.viikkotehtv1.model.Task

@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    onGoCalendar: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tehtävälista", style = MaterialTheme.typography.headlineSmall)

            IconButton(onClick = onGoCalendar) {
                Icon(imageVector = Icons.Filled.List, contentDescription = "Kalenteri")
            }
        }

        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    onToggleDone = { viewModel.toggleDone(task.id) },
                    onClick = { selectedTask = task }
                )
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("+")
        }

        if (showAddDialog) {
            AddTaskDialog(
                onDismiss = { showAddDialog = false },
                onSave = { viewModel.addTask(it) }
            )
        }

        selectedTask?.let {
            TaskDetailDialog(
                task = it,
                onDismiss = { selectedTask = null },
                onSave = { updated ->
                    viewModel.updateTask(updated)
                    selectedTask = null
                },
                onDelete = {
                    viewModel.removeTask(it.id)
                    selectedTask = null
                }
            )
        }
    }
}

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Lisää tehtävä") },
        text = {
            Column {
                OutlinedTextField(title, { title = it }, label = { Text("Nimi") })
                OutlinedTextField(description, { description = it }, label = { Text("Kuvaus") })
                OutlinedTextField(dueDate, { dueDate = it }, label = { Text("Deadline") })
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank()) {
                    onSave(
                        Task(
                            id = System.currentTimeMillis().toInt(),
                            title = title,
                            description = description,
                            priority = 1,
                            dueDate = dueDate,
                            done = false
                        )
                    )
                    onDismiss()
                }
            }) {
                Text("Tallenna")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Peruuta")
            }
        }
    )
}