package com.nairi.githubrepofinder.data.repo

import com.nairi.githubrepofinder.core.base_request.BaseRequest
import com.nairi.githubrepofinder.data.api.WebApi
import com.nairi.githubrepofinder.data.db.DownloadedRepoDao
import com.nairi.githubrepofinder.domain.GithubRepository
import com.nairi.githubrepofinder.domain.mapper.DownloadedRepoDataMapper
import com.nairi.githubrepofinder.domain.mapper.DownloadedRepoMapper
import com.nairi.githubrepofinder.domain.mapper.GithubItemResponseMapper
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem
import com.nairi.githubrepofinder.domain.model.GithubRepoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GithubRepositoryImpl(
    private val api: WebApi,
    private val dao: DownloadedRepoDao,
    private val mapper: GithubItemResponseMapper,
    private val downloadedRepoMapper: DownloadedRepoMapper,
    private val downloadedRepoDataMapper: DownloadedRepoDataMapper
) : GithubRepository {

    override suspend fun getItemsByQuery(query: String): BaseRequest<List<GithubRepoItem>> {
        return try {
            BaseRequest.Success(
                mapper.mapTo(
                    input = api.getRepos(query = query)
                )
            )
        } catch (e: Exception) {
            BaseRequest.Error(e)
        }
    }

    override fun getDownloadedRepos(): Flow<List<DownloadedRepoItem>> =
        dao.getDownloadedRepos().map {
            downloadedRepoMapper.mapTo(it)
        }

    override suspend fun insertRepo(repoItem: DownloadedRepoItem) {
        dao.insertRepo(downloadedRepoDataMapper.mapTo(repoItem))
    }
}