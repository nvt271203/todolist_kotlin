package com.example.todolist_kotlin.data.remote.models

data class TaskDtoItem(
    val description: String,
    val dueDate: String,
    val id: String,
    val isCompleted: Boolean,
    val priority: String,
    val reminderEnabled: String,
    val tags: String
)