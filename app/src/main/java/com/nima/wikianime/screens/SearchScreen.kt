package com.nima.wikianime.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apollographql.apollo3.api.ApolloResponse
import com.nima.wikianime.R
import com.nima.wikianime.SearchQuery
import com.nima.wikianime.components.ExposedMenuList
import com.nima.wikianime.components.ThumbnailItemDetailed
import com.nima.wikianime.navigation.Screens
import com.nima.wikianime.type.MediaFormat
import com.nima.wikianime.type.MediaSeason
import com.nima.wikianime.type.MediaSort
import com.nima.wikianime.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel
) {

    val genres = produceState<List<String?>?>(initialValue = emptyList()){
        value = listOf("All")+viewModel.getAllGenres().data?.GenreCollection!!
    }.value

    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = listOf("All")+
            (1940..thisYear+1).toList().toString().removePrefix("[")
                .removeSuffix("]").split(",").map{it.trim()}

    val seasons = listOf("All", MediaSeason.SPRING.name, MediaSeason.SUMMER.name,
        MediaSeason.FALL.name, MediaSeason.WINTER.name)

    val formats = listOf("All", MediaFormat.MANGA.name, MediaFormat.MOVIE.name,
        MediaFormat.MUSIC.name, MediaFormat.NOVEL.name, MediaFormat.ONA.name,
        MediaFormat.ONE_SHOT.name, MediaFormat.OVA.name, MediaFormat.SPECIAL.name,
        MediaFormat.TV.name, MediaFormat.TV_SHORT.name

    )

    val sorts = listOf("None", MediaSort.TITLE_ENGLISH.name, MediaSort.TITLE_ENGLISH_DESC.name,
        MediaSort.POPULARITY.name,
        MediaSort.POPULARITY_DESC.name, MediaSort.SCORE.name,
        MediaSort.SCORE_DESC.name, MediaSort.TRENDING.name,
        MediaSort.TRENDING_DESC.name, MediaSort.FAVOURITES.name, MediaSort.FAVOURITES_DESC.name
    )

    var page by rememberSaveable{
        mutableStateOf(1)
    }

    var search: String? by rememberSaveable{
        mutableStateOf(null)
    }

    var genre: String? by rememberSaveable {
        mutableStateOf(null)
    }

    var seasonYear: Int? by rememberSaveable {
        mutableStateOf(null)
    }

    var season: MediaSeason? by rememberSaveable {
        mutableStateOf(null)
    }

    var format: MediaFormat? by rememberSaveable {
        mutableStateOf(null)
    }

    var sort: List<MediaSort>? by rememberSaveable {
        mutableStateOf(null)
    }

    var searchResult: ApolloResponse<SearchQuery.Data>? by remember{
        mutableStateOf(null)
    }
    
    var showMore by rememberSaveable {
        mutableStateOf(false)
    }

    var genreExpanded by remember {
        mutableStateOf(false)
    }

    var yearExpanded by remember {
        mutableStateOf(false)
    }

    var seasonExpanded by remember{
        mutableStateOf(false)
    }

    var formatExpanded by remember{
        mutableStateOf(false)
    }

    var sortExpanded by remember{
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = page){
        launch {
            searchResult = viewModel.getSearchResult(page, search, genre, seasonYear, season, format, sort)
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
                    TextField(
                        value = search ?: "",
                        onValueChange = {
                            if (it.isBlank()){
                                search = null
                            }else{
                                search = it
                            }
                        },
                        modifier = Modifier
                            .weight(1f),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        placeholder = {
                            Text(text = "Search",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                //search
                                showMore = false
                                runBlocking {
                                    launch{
                                        searchResult = viewModel.getSearchResult(
                                            page,
                                            search,
                                            genre,
                                            seasonYear,
                                            season,
                                            format,
                                            sort
                                        )
                                    }
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                            }
                        },
                        singleLine = true,
                        shape = CircleShape,
                        colors = TextFieldDefaults.textFieldColors(
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            containerColor = Color.Transparent,
                        )
                    )
                    
                    IconButton(onClick = { 
                        showMore = !showMore
                    }) {
                        Icon(painter = if (showMore)
                            painterResource(id = R.drawable.ic_baseline_expand_more_24)
                            else painterResource(id = R.drawable.ic_baseline_expand_less_24),
                            contentDescription = null)
                    }
                }

                AnimatedVisibility(visible = showMore) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        // genre menu
                        ExposedMenuList(
                            modifier = Modifier
                                .weight(1f)
                                .padding(3.dp),
                            expanded = genreExpanded,
                            onExpandChanged = {
                                genreExpanded = !genreExpanded
                            },
                            onDismiss = {
                                genreExpanded = false
                            },
                            options = genres ?: listOf("All"),
                            selectedOptionText = if (genre != null) genre!! else "All",
                            label = "Genre",
                            onClick = {
                                if (it == "All"){
                                    genre = null
                                }else{
                                    genre = it
                                }
                                genreExpanded = false
                            }
                        )

                        //year Menu
                        ExposedMenuList(
                            modifier = Modifier
                                .weight(1f)
                                .padding(3.dp),
                            expanded = yearExpanded,
                            onExpandChanged = { yearExpanded = !yearExpanded },
                            onDismiss = { yearExpanded = false },
                            options = years,
                            selectedOptionText = if (seasonYear != null) seasonYear.toString() else "All",
                            label = "Season Year",
                            onClick = {
                                if (it == "All"){
                                    seasonYear = null
                                }else{
                                    seasonYear = it.toInt()
                                }
                                yearExpanded = false
                            }
                        )
                    }
                }
                AnimatedVisibility(visible = showMore) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        //season menu
                        ExposedMenuList(
                            modifier = Modifier
                                .weight(1f)
                                .padding(3.dp),
                            expanded = seasonExpanded,
                            onExpandChanged = { seasonExpanded = !seasonExpanded },
                            onDismiss = { seasonExpanded = false },
                            options = seasons,
                            selectedOptionText =
                            if (season != null) {
                                when (season) {
                                    MediaSeason.SUMMER -> MediaSeason.SUMMER.name
                                    MediaSeason.SPRING -> MediaSeason.SPRING.name
                                    MediaSeason.WINTER -> MediaSeason.WINTER.name
                                    MediaSeason.FALL -> MediaSeason.FALL.name
                                    else -> ""
                                }
                            }else "All",
                            label = "Season",
                            onClick = {
                                if (it == "All"){
                                    season = null
                                }else{
                                    when(it){
                                        MediaSeason.WINTER.name -> season = MediaSeason.WINTER
                                        MediaSeason.FALL.name -> season = MediaSeason.FALL
                                        MediaSeason.SPRING.name -> season = MediaSeason.SPRING
                                        MediaSeason.SUMMER.name -> season = MediaSeason.SUMMER
                                    }
                                }
                                seasonExpanded = false
                            }
                        )

                        //format menu
                        ExposedMenuList(
                            modifier = Modifier
                                .weight(1f)
                                .padding(3.dp),
                            expanded = formatExpanded,
                            onExpandChanged = { formatExpanded = !formatExpanded },
                            onDismiss = { formatExpanded = false },
                            options = formats,
                            selectedOptionText =
                            if (format != null){
                                when(format){
                                    MediaFormat.OVA -> MediaFormat.OVA.name
                                    MediaFormat.TV_SHORT -> MediaFormat.TV_SHORT.name
                                    MediaFormat.MANGA -> MediaFormat.MANGA.name
                                    MediaFormat.TV -> MediaFormat.TV.name
                                    MediaFormat.SPECIAL -> MediaFormat.SPECIAL.name
                                    MediaFormat.ONA -> MediaFormat.ONA.name
                                    MediaFormat.ONE_SHOT -> MediaFormat.ONE_SHOT.name
                                    MediaFormat.NOVEL -> MediaFormat.NOVEL.name
                                    MediaFormat.MOVIE -> MediaFormat.MOVIE.name
                                    MediaFormat.MUSIC -> MediaFormat.MUSIC.name
                                    else -> ""
                                }
                            }
                            else "All",
                            label = "Format",
                            onClick = {
                                if (it == "All"){
                                    format = null
                                }else{
                                    when(it){
                                        MediaFormat.OVA.name -> format = MediaFormat.OVA
                                        MediaFormat.TV_SHORT.name -> format = MediaFormat.TV_SHORT
                                        MediaFormat.MANGA.name -> format = MediaFormat.MANGA
                                        MediaFormat.TV.name -> format = MediaFormat.TV
                                        MediaFormat.SPECIAL.name -> format = MediaFormat.SPECIAL
                                        MediaFormat.ONA.name -> format = MediaFormat.ONA
                                        MediaFormat.ONE_SHOT.name -> format = MediaFormat.ONE_SHOT
                                        MediaFormat.NOVEL.name -> format = MediaFormat.NOVEL
                                        MediaFormat.MOVIE.name -> format = MediaFormat.MOVIE
                                        MediaFormat.MUSIC.name -> format = MediaFormat.MUSIC
                                    }
                                }
                                formatExpanded = false
                            }
                        )
                    }
                }
                AnimatedVisibility(visible = showMore) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        //sort menu
                        ExposedMenuList(
                            modifier = Modifier
                                .weight(1f)
                                .padding(3.dp),
                            expanded = sortExpanded,
                            onExpandChanged = { sortExpanded = !sortExpanded },
                            onDismiss = { sortExpanded = false },
                            options = sorts,
                            selectedOptionText =
                            if (sort != null) {
                                when(sort!![0]){
                                    MediaSort.TITLE_ENGLISH -> MediaSort.TITLE_ENGLISH.name
                                    MediaSort.TITLE_ENGLISH_DESC -> MediaSort.TITLE_ENGLISH_DESC.name
                                    MediaSort.POPULARITY -> MediaSort.POPULARITY.name
                                    MediaSort.POPULARITY_DESC -> MediaSort.POPULARITY_DESC.name
                                    MediaSort.SCORE -> MediaSort.SCORE.name
                                    MediaSort.SCORE_DESC -> MediaSort.SCORE_DESC.name
                                    MediaSort.TRENDING -> MediaSort.TRENDING.name
                                    MediaSort.TRENDING_DESC -> MediaSort.TRENDING_DESC.name
                                    MediaSort.FAVOURITES -> MediaSort.FAVOURITES.name
                                    MediaSort.FAVOURITES_DESC -> MediaSort.FAVOURITES_DESC.name
                                    else -> ""
                                }
                            }
                            else "None",
                            label = "Sort By",
                            onClick = {
                                if (it == "None"){
                                    sort = null
                                }else{
                                    when(it){
                                        MediaSort.TITLE_ENGLISH.name -> {
                                            sort = listOf(MediaSort.TITLE_ENGLISH)
                                        }
                                        MediaSort.TITLE_ENGLISH_DESC.name -> {
                                            sort = listOf(MediaSort.TITLE_ENGLISH_DESC)
                                        }
                                        MediaSort.POPULARITY.name -> {
                                            sort = listOf(MediaSort.POPULARITY)
                                        }
                                        MediaSort.POPULARITY_DESC.name -> {
                                            sort = listOf(MediaSort.POPULARITY_DESC)
                                        }
                                        MediaSort.SCORE.name -> {
                                            sort = listOf(MediaSort.SCORE)
                                        }
                                        MediaSort.SCORE_DESC.name -> {
                                            sort = listOf(MediaSort.SCORE_DESC)
                                        }
                                        MediaSort.TRENDING.name -> {
                                            sort = listOf(MediaSort.TRENDING)
                                        }
                                        MediaSort.TRENDING_DESC.name -> {
                                            sort = listOf(MediaSort.TRENDING_DESC)
                                        }
                                        MediaSort.FAVOURITES.name -> {
                                            sort = listOf(MediaSort.FAVOURITES)
                                        }
                                        MediaSort.FAVOURITES_DESC.name -> {
                                            sort = listOf(MediaSort.FAVOURITES_DESC)
                                        }
                                    }
                                }
                                sortExpanded = false
                            }
                        )

                        FilledIconButton(onClick = {
                            showMore = false
                            runBlocking {
                                launch {
                                    searchResult = viewModel.getSearchResult(page, search, genre, seasonYear, season, format, sort)
                                }
                            }
                        },
                            shape = CircleShape,
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    }
                }
                AnimatedVisibility(visible = searchResult != null) {
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
                        Text(text = "$page/${searchResult?.data?.Page?.pageInfo?.lastPage} Pages",
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        IconButton(onClick = {
                            page++
                            //page after
                        },
                            enabled = page != searchResult?.data?.Page?.pageInfo?.lastPage &&
                                    searchResult?.data?.Page?.pageInfo?.hasNextPage == true
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
                                contentDescription = null)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            page = searchResult?.data?.Page?.pageInfo?.lastPage!!
                        },
                            enabled = page != searchResult?.data?.Page?.pageInfo?.lastPage &&
                                    searchResult?.data?.Page?.pageInfo?.hasNextPage == true
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_last_page_24),
                                contentDescription = null)
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = searchResult == null) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
        AnimatedVisibility(visible = searchResult != null) {
            if (searchResult != null){
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                ) {
                    items(items = searchResult?.data?.Page?.media!!) {
                        ThumbnailItemDetailed(media = it!!,
                            onClick = { id ->
                                navController.navigate(Screens.MediaScreen.name+"/$id")
                            },
                            onGenreClicked = {genreString ->
                                if (genreString != genre) {
                                    navController.navigate(Screens.GenreScreen.name + "/$genreString") }
                            }
                        )
                    }
                }
            }
        }
    }

}