package com.nairi.githubrepofinder.core.base_request

sealed class BaseRequest<out T> {
    data class Success<out T>(val data: T) : BaseRequest<T>()
    data class Error(val error: Exception) : BaseRequest<Nothing>()
}