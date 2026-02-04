package com.example.viikkotehtv1.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikkotehtv1.ViewModel.TaskViewModel
import com.example.viikkotehtv1.model.Task

@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onGoHome: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    val grouped = tasks.groupBy { it.dueDate }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Kalenteri", style = MaterialTheme.typography.headlineSmall)

            Button(onClick = onGoHome) {
                Text("Lista")
            }
        }

        Spacer(Modifier.height(8.dp))

        val groupedTasks = tasks.groupBy { it.dueDate }

        LazyColumn {
            groupedTasks.forEach { (date, dayTasks) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(dayTasks) { task ->
                    TaskCard(
                        task = task,
                        onToggleDone = { viewModel.toggleDone(task.id) },
                        onClick = { selectedTask = task }
                    )
                }
            }
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