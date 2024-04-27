package com.nairi.githubrepofinder.data.model

data class GithubReposResponse(
    val items: List<GithubRepoItemResponse>
)

data class GithubRepoItemResponse(
    val downloads_url: String,
    val full_name: String,
    val name: String,
    val node_id: String,
    val svn_url: String,
)