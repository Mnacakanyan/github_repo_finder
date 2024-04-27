package com.nairi.githubrepofinder.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DownloadedRepoEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo("user_name")
    val userName: String,

    @ColumnInfo("repo_name")
    val repoName: String
)
