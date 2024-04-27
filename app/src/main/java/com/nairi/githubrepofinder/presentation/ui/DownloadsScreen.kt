package com.nairi.githubrepofinder.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nairi.githubrepofinder.R
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem
import com.nairi.githubrepofinder.presentation.viewmodel.DownloadsUiState
import com.nairi.githubrepofinder.presentation.viewmodel.MainUiState

@Composable
fun DownloadsScreen(
    state: DownloadsUiState
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (listRef, progress) = createRefs()
        val centerModifier = Modifier.constrainAs(progress) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
            height = Dimension.wrapContent

        }
        when (state) {
            DownloadsUiState.Loading -> CircularProgressIndicator(modifier = centerModifier)
            DownloadsUiState.Empty -> Unit
            is DownloadsUiState.Error -> ErrorData(errorText = state.error, modifier = centerModifier)
            is DownloadsUiState.Success -> DownloadedRepos(
                items = state.list,
                modifier = Modifier
                    .constrainAs(listRef) {
                        top.linkTo(parent.top, 16.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                centerModifier = centerModifier
            )
        }


    }
}

@Composable
private fun DownloadedRepos(
    modifier: Modifier = Modifier,
    centerModifier: Modifier = Modifier,
    items: List<DownloadedRepoItem>
) {
    if (items.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(items, key = {it.id}) { item ->
                DownloadedRepo(repo = item)
            }
        }
    } else {
        Text(
            text = stringResource(id = R.string.no_downloaded_repo_found),
            modifier = centerModifier
        )
    }
}

@Composable
private fun DownloadedRepo(
    repo: DownloadedRepoItem
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = repo.userName,
            fontSize = 20.sp,
        )
        Text(text = repo.repoName)
    }
}