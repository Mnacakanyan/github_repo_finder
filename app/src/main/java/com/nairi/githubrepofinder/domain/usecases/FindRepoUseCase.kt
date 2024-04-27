package com.nairi.githubrepofinder.domain.usecases

import com.nairi.githubrepofinder.domain.GithubRepository

class FindRepoUseCase(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(query: String) = repository.getItemsByQuery(query)
}