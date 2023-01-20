package com.nima.wikianime.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apollographql.apollo3.api.ApolloResponse
import com.nima.wikianime.R
import com.nima.wikianime.TrendingNowQuery
import com.nima.wikianime.components.ThumbnailItemDetailed
import com.nima.wikianime.navigation.Screens
import com.nima.wikianime.viewmodel.TrendingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun TrendingScreen(
    navController: NavController,
    viewModel: TrendingViewModel
) {

    var page by rememberSaveable{
        mutableStateOf(1)
    }

//    var trendingNow = produceState<ApolloResponse<TrendingNowQuery.Data>?>(initialValue = null){
//        value = viewModel.getTrendingNow(page)
//    }.value

    var trendingNow: ApolloResponse<TrendingNowQuery.Data>? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = page){
        launch{
            trendingNow = viewModel.getTrendingNow(page)
        }
    }

    BackHandler(true) {
        if (page > 1){
            page --
        }else{
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            shadowElevation = 8.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                    Text(
                        text = "Trending Now",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
                AnimatedVisibility(visible = trendingNow != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = {
                            page = 1
                        },
                            enabled = page != 1
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_first_page_24),
                                contentDescription = null)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            page--
                            //page before
                        },
                            enabled = page != 1
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_left_24),
                                contentDescription = null)
                        }
                        Text(text = "$page/${trendingNow?.data?.Page?.pageInfo?.lastPage} Pages",
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        IconButton(onClick = {
                            page++
                            //page after
                        },
                            enabled = page != trendingNow?.data?.Page?.pageInfo?.lastPage &&
                                    trendingNow?.data?.Page?.pageInfo?.hasNextPage == true
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
                                contentDescription = null)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            page = trendingNow?.data?.Page?.pageInfo?.lastPage!!
                        },
                            enabled = page != trendingNow?.data?.Page?.pageInfo?.lastPage &&
                                    trendingNow?.data?.Page?.pageInfo?.hasNextPage == true
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_last_page_24),
                                contentDescription = null)
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = trendingNow == null) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
        AnimatedVisibility(visible = trendingNow != null) {
            if (trendingNow != null){
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                ) {
                    items(items = trendingNow?.data?.Page?.media!!) {
                        ThumbnailItemDetailed(media = it!!,
                            onClick = { id ->
                                navController.navigate(Screens.MediaScreen.name+"/$id")
                            },
                            onGenreClicked = {genre ->
                                navController.navigate(Screens.GenreScreen.name+"/$genre")
                            }
                        )
                    }
                }
            }
        }
    }
}