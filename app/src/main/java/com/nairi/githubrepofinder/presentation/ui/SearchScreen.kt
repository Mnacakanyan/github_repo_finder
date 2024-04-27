package com.nairi.githubrepofinder.presentation.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nairi.githubrepofinder.R
import com.nairi.githubrepofinder.domain.model.DownloadedRepoItem
import com.nairi.githubrepofinder.domain.model.GithubRepoItem
import com.nairi.githubrepofinder.presentation.viewmodel.MainUiState

typealias OnAction = (String) -> Unit

@Composable
fun SearchScreen(
    state: MainUiState,
    searchText: String,
    onQueryChanged: OnAction,
    onSearch: OnAction,
    onDownload: (DownloadedRepoItem) -> Unit
) {
    val context = LocalContext.current

    var text by remember {
        mutableStateOf(searchText)
    }

    var emptyText by remember(key1 = text) {
        mutableStateOf(false)
    }
    var animationEnded by remember {
        mutableStateOf(false)
    }

    val xAxisAnim by animateFloatAsState(
        targetValue = if (emptyText) 16f else 0f,
        label = "translationX",
        animationSpec = repeatable(
            iterations = 4,
            animation = tween(durationMillis = 50)
        ),
        finishedListener = {
            animationEnded = true
            emptyText = false
        }
    )

    LaunchedEffect(key1 = animationEnded) {
        if (animationEnded) {
            animationEnded = false
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 16.dp, end = 8.dp)
    ) {
        val (search, repoList, progress) = createRefs()

        val centeredModifier = Modifier.constrainAs(progress) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        Row(
            modifier = Modifier.constrainAs(search) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = text,
                onValueChange = { newQuery ->
                    text = newQuery
                    onQueryChanged.invoke(newQuery)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_text))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    if (text.isNotEmpty()) {
                        onSearch.invoke(text)
                        return@KeyboardActions
                    }
                    emptyText = true
                }),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .graphicsLayer {
                        translationX = if (animationEnded) 0f else xAxisAnim
                    },
            )
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        if (text.isNotEmpty()) {
                            onSearch.invoke(text)
                            return@clickable
                        }
                        emptyText = true
                    }
            )
        }
        when (state) {
            MainUiState.Empty -> Unit
            MainUiState.Loading -> CircularProgressIndicator(
                modifier = centeredModifier
            )

            is MainUiState.Success -> RepoList(
                modifier = Modifier.constrainAs(repoList) {
                    top.linkTo(search.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                data = state.list,
                centerModifier = centeredModifier,
                onClickItem = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(it)
                    context.startActivity(intent)
                },
                onDownload = { userRepoName ->
                    val url = "https://api.github.com/repos/${userRepoName.userRepoName}/zipball"

                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    context.startActivity(intent)
                    onDownload(
                        DownloadedRepoItem(
                            id = userRepoName.id,
                            userName = userRepoName.userRepoName.split("/").firstOrNull().orEmpty(),
                            repoName = userRepoName.title
                        )
                    )
                }
            )

            is MainUiState.Error -> ErrorData(
                modifier = centeredModifier,
                errorText = state.error
            )
        }

    }
}

@Composable
private fun RepoList(
    modifier: Modifier = Modifier,
    centerModifier: Modifier,
    data: List<GithubRepoItem>,
    onClickItem: OnAction,
    onDownload: (GithubRepoItem) -> Unit,
) {
    if (data.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(data, key = { it.id }) { item ->
                GithubRepoItemData(
                    item = item,
                    onClickItem = onClickItem,
                    onDownload = onDownload
                )
            }
        }
    } else {
        Text(
            text = stringResource(id = R.string.no_repo_found),
            modifier = centerModifier
        )
    }
}

@Composable
private fun GithubRepoItemData(
    item: GithubRepoItem,
    onClickItem: OnAction,
    onDownload: (GithubRepoItem) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(
                vertical = 8.dp
            )
            .clickable {
                onClickItem(item.url)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.title,
        )
        Icon(
            imageVector = Icons.Filled.Download,
            contentDescription = null,
            modifier = Modifier.clickable {
                onDownload(item)
            }
        )

    }

}

@Composable
internal fun ErrorData(
    modifier: Modifier = Modifier,
    errorText: String
) {
    Text(
        text = errorText,
        modifier = modifier
    )
}