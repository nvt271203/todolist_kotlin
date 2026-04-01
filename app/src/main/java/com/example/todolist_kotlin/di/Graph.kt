package com.hoods.taskmanagement.di

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todolist_kotlin.data.local.AppDatabase
import com.example.todolist_kotlin.data.remote.TaskApiService
import com.hoods.taskmanagement.data.repository.TaskRepository
import com.hoods.taskmanagement.data.repository.TaskRepositoryImpl
import com.hoods.taskmanagement.data.worker.SyncWorker

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Graph {
    lateinit var repository: TaskRepository
    private const val BASE_URL = "https://691411c7f34a2ff1170e0237.mockapi.io/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    val apiService: TaskApiService by lazy {
        retrofit.create(TaskApiService::class.java)
    }
    fun provide(ctx: Context){
        repository = TaskRepositoryImpl(
            taskDao = AppDatabase.getDatabase(ctx).taskDao(),
            apiService = apiService,
            workManager = WorkManager.getInstance(ctx)
        )
        setPeriodicSyncRequest(ctx)
    }
    private fun setPeriodicSyncRequest(ctx: Context){
        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            1,
            TimeUnit.HOURS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
                    .build()
            ).build()
        WorkManager.getInstance(ctx).enqueueUniquePeriodicWork(
            "syncData",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )

    }

}