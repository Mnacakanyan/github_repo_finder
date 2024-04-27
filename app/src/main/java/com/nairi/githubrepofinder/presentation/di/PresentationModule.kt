package com.nairi.githubrepofinder.presentation.di

import com.nairi.githubrepofinder.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        MainViewModel(
            findRepoUseCase = get(),
            getDownloadedReposUseCase = get(),
            addDownloadedRepoUseCase = get()
        )
    }
}