package com.nairi.githubrepofinder.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nairi.githubrepofinder.data.model.DownloadedRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedRepoDao {

    @Query("SELECT * FROM DownloadedRepoEntity")
    fun getDownloadedRepos() : Flow<List<DownloadedRepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(item: DownloadedRepoEntity)
}