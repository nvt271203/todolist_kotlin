package com.example.todolist_kotlin

import android.app.Application
import com.hoods.taskmanagement.di.Graph

class TaskManagementApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}