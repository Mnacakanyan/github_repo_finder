package com.nairi.githubrepofinder.presentation.viewmodel

import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem

sealed class DownloadsUiState {
    data object Empty : DownloadsUiState()
    data object Loading : DownloadsUiState()
    data class Success(val list: List<DownloadedRepoItem> = emptyList()) : DownloadsUiState()
    data class Error(val error: String): DownloadsUiState()
}