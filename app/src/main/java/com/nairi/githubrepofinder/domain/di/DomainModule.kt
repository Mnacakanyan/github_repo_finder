package com.nairi.githubrepofinder.domain.di

import com.nairi.githubrepofinder.domain.usecases.AddDownloadedRepoUseCase
import com.nairi.githubrepofinder.domain.usecases.FindRepoUseCase
import com.nairi.githubrepofinder.domain.usecases.GetDownloadedReposUseCase
import org.koin.core.scope.get
import org.koin.dsl.module

val domainModule = module {
    single {
        FindRepoUseCase(
            repository = get()
        )
    }

    single {
        GetDownloadedReposUseCase(
            repository = get()
        )
    }

    single {
        AddDownloadedRepoUseCase(
            repository = get()
        )
    }

}