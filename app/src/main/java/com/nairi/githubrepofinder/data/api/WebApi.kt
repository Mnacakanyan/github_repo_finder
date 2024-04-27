package com.nairi.githubrepofinder.data.api

import com.nairi.githubrepofinder.data.model.GithubReposResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebApi {
    companion object {
        private const val SEARCH = "/search/repositories"
        private const val DOWNLOAD = "/repos/{user}/{repo}/zipball"
    }

    @GET(SEARCH)
    suspend fun getRepos(
        @Query("q") query: String
    ) : GithubReposResponse

    @GET(DOWNLOAD)
    suspend fun downloadRepo(
        @Path("user") user: String,
        @Path("repo") repo: String
    )
}