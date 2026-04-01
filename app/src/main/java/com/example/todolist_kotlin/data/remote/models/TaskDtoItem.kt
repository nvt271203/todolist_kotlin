package com.example.todolist_kotlin.data.remote.models

data class TaskDtoItem(
    val description: String,
    val title: String,
    val dueDate: String,
    val id: Int,
    val priority: String,
    val reminderEnabled: Boolean,
    val tags: String,
    val isCompleted: Boolean

)