package com.nairi.githubrepofinder.presentation.routes

sealed class AppRoutes(val route: String) {
    data object SearchScreen : AppRoutes(route = "search")
    data object DownloadsScreen : AppRoutes(route = "downloads")
}