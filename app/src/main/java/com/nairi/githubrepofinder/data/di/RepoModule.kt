package com.nairi.githubrepofinder.data.di

import com.nairi.githubrepofinder.data.repo.GithubRepositoryImpl
import com.nairi.githubrepofinder.domain.GithubRepository
import com.nairi.githubrepofinder.domain.mapper.DownloadedRepoDataMapper
import com.nairi.githubrepofinder.domain.mapper.DownloadedRepoMapper
import com.nairi.githubrepofinder.domain.mapper.GithubItemResponseMapper
import org.koin.dsl.module

val repoModule = module {
    single<GithubRepository> {
        GithubRepositoryImpl(
            api = get(),
            mapper = get(),
            dao = get(),
            downloadedRepoMapper = get(),
            downloadedRepoDataMapper = get()
        )
    }

    single {
        GithubItemResponseMapper()
    }

    single {
        DownloadedRepoMapper()
    }

    single {
        DownloadedRepoDataMapper()
    }
}