package com.nairi.githubrepofinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nairi.githubrepofinder.data.model.DownloadedRepoEntity

@Database(entities = [DownloadedRepoEntity::class], version = 1)
abstract class DownloadedReposDb: RoomDatabase() {
    abstract fun getDownloadedDao() : DownloadedRepoDao
}