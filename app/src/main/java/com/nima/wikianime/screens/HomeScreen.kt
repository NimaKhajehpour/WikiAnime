package com.nima.wikianime.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apollographql.apollo3.api.ApolloResponse
import com.nima.wikianime.*
import com.nima.wikianime.R
import com.nima.wikianime.components.ThumbnailItem
import com.nima.wikianime.navigation.Screens
import com.nima.wikianime.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen (
    navController: NavController,
    viewModel: HomeViewModel
){
    val context = LocalContext.current

    val themeDataStore = ThemeDataStore(context)

    val isDark =
        themeDataStore.getTheme.collectAsState(initial = false).value ?: false

    val scope = rememberCoroutineScope()

    val allGenres = produceState<ApolloResponse<AllGenresQuery.Data>?>(initialValue = null){
        value = viewModel.getAllGenres()
    }.value

    val trendingNow = produceState<ApolloResponse<TrendingNowQuery.Data>?>(initialValue = null){
        value = viewModel.getTrendingNow()
    }.value

    val popularAllTime = produceState<ApolloResponse<PopularAllTimeQuery.Data>?>(initialValue = null){
        value = viewModel.getPopularAllTime()
    }.value

    val topTen = produceState<ApolloResponse<TopHundredQuery.Data>?>(initialValue = null){
        value = viewModel.getTopTen()
    }.value

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            shadowElevation = 8.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "WikiAnime",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    //Go To Search
                    navController.navigate(Screens.SearchScreen.name)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
                IconButton(onClick = {
                    scope.launch {
                        themeDataStore.saveTheme(!isDark)
                    }
                },
                    modifier = Modifier.size(48.dp),
                ) {

                    AnimatedContent(targetState = isDark) {
                        when (it){
                            true ->{
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_light_mode_24),
                                        contentDescription = null,
                                    )
                                }
                            }else -> {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_dark_mode_24),
                                    contentDescription = null,
                                )
                            }
                        }
                        }
                    }

                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.padding(top = 16.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Genres",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                AnimatedVisibility(visible = allGenres == null) {
                    CircularProgressIndicator(modifier = Modifier.size(10.dp),
                        strokeWidth = 1.dp
                    )
                }
            }
            AnimatedVisibility(visible = allGenres != null){
                if (allGenres?.data != null
                    && allGenres.data?.GenreCollection!!.isNotEmpty()){
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        items(items = allGenres.data!!.GenreCollection!!){
                            OutlinedButton(onClick = {
                                // go to genre
                                navController.navigate(Screens.GenreScreen.name+"/$it")
                            },
                                shape = CircleShape,
                                contentPadding = PaddingValues(16.dp),
                                modifier = Modifier.padding(horizontal = 4.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.tertiary,
                                ),
                                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiaryContainer)
                            ) {
                                Text(text = it ?: "")
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(top = 32.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Trending Now",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                AnimatedVisibility(visible = trendingNow == null) {
                    CircularProgressIndicator(modifier = Modifier.size(10.dp),
                        strokeWidth = 1.dp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = {
                    // go to trending
                    navController.navigate(Screens.TrendingScreen.name)
                },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(text = "View All",
                        style = MaterialTheme.typography.bodyMedium,
                        )
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            AnimatedVisibility(visible = trendingNow != null){
                if (trendingNow?.data != null
                    && trendingNow.data?.Page?.media!!.isNotEmpty()){
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 15.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                    ) {
                        items(items = trendingNow.data!!.Page?.media!!){
                            ThumbnailItem(media = it!!){id ->
                                navController.navigate(Screens.MediaScreen.name+"/$id")
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.padding(top = 32.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Popular All Time",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                AnimatedVisibility(visible = popularAllTime == null) {
                    CircularProgressIndicator(modifier = Modifier.size(10.dp),
                        strokeWidth = 1.dp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = {
                    // go to popular
                    navController.navigate(Screens.PopularScreen.name)
                },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )) {
                    Text(text = "View All",
                        style = MaterialTheme.typography.bodyMedium,
                        )
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            AnimatedVisibility(visible = popularAllTime != null){
                if (popularAllTime?.data != null
                    && popularAllTime.data?.Page?.media!!.isNotEmpty()){
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 15.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                    ) {
                        items(items = popularAllTime.data!!.Page?.media!!){
                            ThumbnailItem(media = it!!){id ->
                                navController.navigate(Screens.MediaScreen.name+"/$id")
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.padding(top = 32.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Top 10",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                AnimatedVisibility(visible = topTen == null) {
                    CircularProgressIndicator(modifier = Modifier.size(10.dp),
                        strokeWidth = 1.dp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = {
                    // go to top 10
                    navController.navigate(Screens.TopAnimeScreen.name)
                },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )) {
                    Text(text = "View All",
                        style = MaterialTheme.typography.bodyMedium,
                        )
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            AnimatedVisibility(visible = topTen != null){
                if (topTen?.data != null
                    && topTen.data?.Page?.media!!.isNotEmpty()){
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 15.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                    ) {
                        items(items = topTen.data!!.Page?.media!!){
                            ThumbnailItem(media = it!!){id ->
                                navController.navigate(Screens.MediaScreen.name+"/$id")
                            }
                        }
                    }
                }
            }
        }
    }
}