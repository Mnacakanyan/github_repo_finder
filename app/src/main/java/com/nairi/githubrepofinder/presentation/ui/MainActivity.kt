package com.nairi.githubrepofinder.presentation.ui

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nairi.githubrepofinder.presentation.routes.AppRoutes
import com.nairi.githubrepofinder.presentation.viewmodel.MainViewModel
import com.nairi.githubrepofinder.ui.theme.GithubRepoFinderTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen().apply {
                setKeepOnScreenCondition {
                    viewModel.canGoNextPage.value
                }
                setOnExitAnimationListener { viewProvider ->
                    val scaleXAnim = ObjectAnimator.ofFloat(
                        viewProvider.iconView,
                        View.SCALE_X,
                        0.7f,
                        0f
                    )
                    scaleXAnim.duration = 1000L
                    val scaleYAnim = ObjectAnimator.ofFloat(
                        viewProvider.iconView,
                        View.SCALE_Y,
                        0.7f,
                        0f
                    )
                    scaleYAnim.duration = 1000L
                    scaleYAnim.doOnEnd {
                        viewProvider.remove()
                    }
                    scaleXAnim.doOnEnd {
                        viewProvider.remove()
                    }

                    scaleYAnim.interpolator = LinearInterpolator()
                    scaleXAnim.interpolator = LinearInterpolator()

                    scaleYAnim.start()
                    scaleXAnim.start()
                }
            }
        }

        setContent {
            val navController = rememberNavController()
            val uiState = viewModel.uiState.value
            val downloadsUiState = viewModel.downloadsUiState.value
            GithubRepoFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            BottomBar { route ->
                                navController.navigate(route)
                            }
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = AppRoutes.SearchScreen.route,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(route = AppRoutes.SearchScreen.route) {
                                SearchScreen(
                                    state = uiState,
                                    searchText = viewModel.searchText,
                                    onQueryChanged = viewModel::updateSearchText,
                                    onSearch = viewModel::searchRepo,
                                    onDownload = viewModel::addRepo
                                )
                            }
                            composable(route = AppRoutes.DownloadsScreen.route) {
                                DownloadsScreen(
                                    state = downloadsUiState
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}



