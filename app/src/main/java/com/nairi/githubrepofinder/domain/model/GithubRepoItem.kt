package com.nairi.githubrepofinder.domain.model

data class GithubRepoItem(
    val id: String,
    val title: String,
    val url: String,
    val downloadUrl: String,
    val userRepoName:String
)