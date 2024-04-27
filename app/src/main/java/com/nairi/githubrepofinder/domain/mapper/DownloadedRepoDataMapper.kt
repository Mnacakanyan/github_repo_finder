package com.nairi.githubrepofinder.domain.mapper

import com.nairi.githubrepofinder.data.model.DownloadedRepoEntity
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem

class DownloadedRepoDataMapper: Mapper<DownloadedRepoItem, DownloadedRepoEntity> {
    override fun mapTo(input: DownloadedRepoItem): DownloadedRepoEntity {
        return DownloadedRepoEntity(
            id = input.id,
            userName = input.userName,
            repoName = input.repoName
        )
    }
}