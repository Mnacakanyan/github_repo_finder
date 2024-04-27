package com.nairi.githubrepofinder.domain.usecases

import com.nairi.githubrepofinder.domain.GithubRepository
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem

class AddDownloadedRepoUseCase(
    private val repository: GithubRepository
) {

    suspend operator fun invoke(repoItem: DownloadedRepoItem) {
        repository.insertRepo(repoItem)
    }
}