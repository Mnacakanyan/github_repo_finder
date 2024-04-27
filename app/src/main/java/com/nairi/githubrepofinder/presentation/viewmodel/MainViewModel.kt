package com.nairi.githubrepofinder.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nairi.githubrepofinder.core.base_request.BaseRequest
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem
import com.nairi.githubrepofinder.domain.usecases.AddDownloadedRepoUseCase
import com.nairi.githubrepofinder.domain.usecases.FindRepoUseCase
import com.nairi.githubrepofinder.domain.usecases.GetDownloadedReposUseCase
import com.nairi.githubrepofinder.utils.toErrorMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
    private val findRepoUseCase: FindRepoUseCase,
    private val getDownloadedReposUseCase: GetDownloadedReposUseCase,
    private val addDownloadedRepoUseCase: AddDownloadedRepoUseCase
): ViewModel() {

    private val _canGoNextPage = MutableStateFlow(false)
    val canGoNextPage = _canGoNextPage.asStateFlow()

    var uiState = mutableStateOf<MainUiState>(MainUiState.Empty)
        private set

    var downloadsUiState = mutableStateOf<DownloadsUiState>(DownloadsUiState.Empty)
        private set

    init {
        getDownloadedRepos()
        viewModelScope.launch {
            delay(2000L)
            _canGoNextPage.update {
                true
            }
        }
    }


    var searchText = ""
        private set

    fun searchRepo(query: String) {
        viewModelScope.launch{
            uiState.value = MainUiState.Loading
            val response = findRepoUseCase(query)
            uiState.value = when(response) {
                is BaseRequest.Error -> MainUiState.Error(response.error.toErrorMessage())
                is BaseRequest.Success -> MainUiState.Success(response.data)
            }

        }
    }

    fun updateSearchText(newText: String) {
        searchText = newText
    }

    private fun getDownloadedRepos() {
        viewModelScope.launch {
            downloadsUiState.value = DownloadsUiState.Loading
            getDownloadedReposUseCase().collect { repos ->
                downloadsUiState.value = DownloadsUiState.Success(repos)
            }
        }
    }

    fun addRepo(item: DownloadedRepoItem) {
        viewModelScope.launch {
            addDownloadedRepoUseCase(item)
        }

    }

}