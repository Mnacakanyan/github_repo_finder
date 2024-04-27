package com.nairi.githubrepofinder.domain.mapper

import com.nairi.githubrepofinder.data.model.DownloadedRepoEntity
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem

class DownloadedRepoMapper:
    Mapper<List<DownloadedRepoEntity>, List<DownloadedRepoItem>> {

    override fun mapTo(input: List<DownloadedRepoEntity>): List<DownloadedRepoItem> {
        return input.map {
            DownloadedRepoItem(
                id = it.id,
                userName = it.userName,
                repoName = it.repoName
            )
        }
    }
}