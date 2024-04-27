package com.nairi.githubrepofinder.data.di

import androidx.room.Room
import com.nairi.githubrepofinder.data.db.DownloadedRepoDao
import com.nairi.githubrepofinder.data.db.DownloadedReposDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DB_NAME = "downloaded_repo_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = DownloadedReposDb::class.java,
            name = DB_NAME
        ).build()
    }

    single<DownloadedRepoDao> {
        val db = get<DownloadedReposDb>()
        db.getDownloadedDao()
    }
}