package com.nairi.githubrepofinder.domain.usecases

import com.nairi.githubrepofinder.domain.GithubRepository
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem
import kotlinx.coroutines.flow.Flow

class GetDownloadedReposUseCase(
    private val repository: GithubRepository
) {

    operator fun invoke(): Flow<List<DownloadedRepoItem>> {
        return repository.getDownloadedRepos()
    }
}