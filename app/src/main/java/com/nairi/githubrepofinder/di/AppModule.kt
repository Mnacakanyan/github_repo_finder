package com.nairi.githubrepofinder.di

import com.nairi.githubrepofinder.data.di.dataModule
import com.nairi.githubrepofinder.domain.di.domainModule
import com.nairi.githubrepofinder.presentation.di.presentationModule

val appModule = dataModule + domainModule + presentationModule