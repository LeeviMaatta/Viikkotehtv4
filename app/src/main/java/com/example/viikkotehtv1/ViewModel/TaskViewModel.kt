package com.example.viikkotehtv1.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.viikkotehtv1.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.plus

class TaskViewModel : ViewModel()
{
    private val _tasks = MutableStateFlow(
        listOf(
            Task(1, "Osta maitoa", "Muista laktoositon", 1, "2026-01-15", false),
            Task(2, "Tee viikkotehtävä", "Viikko 1", 2, "2026-01-16", false),
            Task(3, "Lenkille", "5 km", 3, "2026-02-15", true),
            Task(4, "Osta mehua", "Omehamehu", 1, "2026-02-16", false),
            Task(5, "Osta mehua", "Appelsiinimehu", 1, "2026-01-25", false)
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks

    fun addTask(task: Task) {
        val updated = _tasks.value.toMutableList()
        updated.add(task)
        _tasks.value = updated
    }

    fun toggleDone(id: Int) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(done = !it.done) else it
        }
    }

    fun removeTask(id: Int) {
        _tasks.value = _tasks.value.filterNot { it.id == id }
    }

    fun updateTask(updated: Task) {
        _tasks.value = _tasks.value.map {
            if (it.id == updated.id) updated else it
        }
    }
/*
    fun filterByDone(done: Boolean): List<Task>
    {
        return tasks.filter { it.done == done }
    }

    fun sortByDueDate()
    {
        tasks = tasks.sortedBy { it.dueDate }
    }*/
}