package com.nairi.githubrepofinder.domain

import com.nairi.githubrepofinder.core.base_request.BaseRequest
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem
import com.nairi.githubrepofinder.domain.model.GithubRepoItem
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getItemsByQuery(query: String) : BaseRequest<List<GithubRepoItem>>

    fun getDownloadedRepos() : Flow<List<DownloadedRepoItem>>

    suspend fun insertRepo(repoItem: DownloadedRepoItem)
}