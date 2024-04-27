package com.nairi.githubrepofinder.presentation.viewmodel

import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem
import com.nairi.githubrepofinder.domain.model.GithubRepoItem

sealed class MainUiState {
    data object Empty : MainUiState()
    data object Loading : MainUiState()
    data class Success(
        val list: List<GithubRepoItem> = emptyList(),

    ) : MainUiState()
    data class Error(val error: String): MainUiState()
}