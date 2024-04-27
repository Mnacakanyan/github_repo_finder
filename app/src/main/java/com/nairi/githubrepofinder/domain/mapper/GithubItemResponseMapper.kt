package com.nairi.githubrepofinder.domain.mapper

import com.nairi.githubrepofinder.data.model.GithubReposResponse
import com.nairi.githubrepofinder.domain.model.GithubRepoItem

class GithubItemResponseMapper
    : Mapper<GithubReposResponse, List<GithubRepoItem>> {

    override fun mapTo(input: GithubReposResponse): List<GithubRepoItem> {
        return input.items.map {
            GithubRepoItem(
                id = it.node_id,
                title = it.name,
                url = it.svn_url,
                downloadUrl = it.downloads_url,
                userRepoName = it.full_name
            )
        }
    }
}