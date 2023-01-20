package com.nima.wikianime.screens

import android.text.Html
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.apollographql.apollo3.api.ApolloResponse
import com.nima.wikianime.MediaPageQuery
import com.nima.wikianime.components.ThumbnailItemDetailed
import com.nima.wikianime.navigation.Screens
import com.nima.wikianime.viewmodel.MediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreen (
    navController: NavController,
    viewModel: MediaViewModel,
    mediaId: Int?
){

    val mediaInfo = produceState<ApolloResponse<MediaPageQuery.Data>?>(initialValue = null){
        value = viewModel.getMediaInfo(mediaId!!)
    }.value

    var tabIndex by remember{
        mutableStateOf(0)
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier.fillMaxWidth(),
            contentColor = MaterialTheme.colorScheme.tertiary,
        ) {
            Tab(selected = tabIndex == 0,
                onClick = { tabIndex = 0 },
                text = { Text(text = "Review", style = MaterialTheme.typography.labelMedium)}
            )
            Tab(selected = tabIndex == 1,
                onClick = { tabIndex = 1 },
                text = { Text(text = "Recommends", style = MaterialTheme.typography.labelMedium)}
            )
        }

        AnimatedVisibility(visible = mediaInfo == null) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
        }
        if (mediaInfo != null){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ){
                AnimatedVisibility(
                    visible = tabIndex == 0
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        if (mediaInfo.data == null) {

                        } else {
                            if (mediaInfo.data!!.Media?.bannerImage != null) {
                                AsyncImage(
                                    model = mediaInfo.data!!.Media?.bannerImage,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                )
                            }
                            if (mediaInfo.data!!.Media?.title?.userPreferred != null) {
                                OutlinedTextField(
                                    value = "${mediaInfo.data!!.Media?.title?.userPreferred}",
                                    onValueChange = {},
                                    label = { Text(text = "Title",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.description != null){
                                OutlinedTextField(
                                    value = Html.fromHtml(mediaInfo.data!!.Media?.description).toString(),
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Description",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.format != null){
                                OutlinedTextField(
                                    value = mediaInfo.data!!.Media?.format?.name?.replace("_", " ")!!,
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Format",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.meanScore != null){
                                OutlinedTextField(
                                    value = "%${mediaInfo.data!!.Media?.meanScore}",
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Mean Score",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.averageScore != null){
                                OutlinedTextField(
                                    value = "%${mediaInfo.data!!.Media?.averageScore}",
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Average Score",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.status != null){
                                OutlinedTextField(
                                    value = "${mediaInfo.data!!.Media?.status?.name}",
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Status",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.startDate != null
                                && mediaInfo.data!!.Media?.startDate?.day != null
                                && mediaInfo.data!!.Media?.startDate?.month != null
                                && mediaInfo.data!!.Media?.startDate?.year != null
                            ){
                                OutlinedTextField(
                                    value = "${mediaInfo.data!!.Media?.startDate?.day}" +
                                        " - ${mediaInfo.data!!.Media?.startDate?.month}" +
                                        " - ${mediaInfo.data!!.Media?.startDate?.year}",
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Start Date",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.popularity != null){
                                OutlinedTextField(
                                    value = "${mediaInfo.data!!.Media?.popularity}",
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Popularity",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.favourites != null){
                                OutlinedTextField(
                                    value = "${mediaInfo.data!!.Media?.favourites}",
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Favorites",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (!mediaInfo.data!!.Media?.studios?.nodes.isNullOrEmpty()){
                                OutlinedTextField(
                                    value = "${mediaInfo.data!!.Media?.studios?.nodes!![0]?.name}",
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Studio",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.duration != null){
                                OutlinedTextField(
                                    value = "${mediaInfo.data!!.Media?.duration}",
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Episode Duration",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (mediaInfo.data!!.Media?.source != null){
                                OutlinedTextField(
                                    value = "${mediaInfo.data!!.Media?.source}",
                                    onValueChange = {},
                                    shape = RoundedCornerShape(10.dp),
                                    readOnly = true,
                                    label = { Text(text = "Source",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary

                                    )},
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                )
                            }
                            if (!mediaInfo.data!!.Media?.genres.isNullOrEmpty()){
                                Text(text = "Genres:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ){
                                    items(items = mediaInfo.data!!.Media?.genres!!){
                                        OutlinedButton(onClick = {
                                            navController.navigate(Screens.GenreScreen.name+"/$it")
                                        },
                                            modifier = Modifier.padding(horizontal = 4.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                            ),
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiaryContainer)
                                        ) {
                                            Text(text = it!!)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(
                    visible = tabIndex == 1
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 6.dp)
                    ){
                        items(mediaInfo.data!!.Media?.recommendations?.nodes.orEmpty()){
                            ThumbnailItemDetailed(media = it!!,
                                onClick = {id ->
                                    navController.navigate(Screens.MediaScreen.name+"/$id")
                                },
                                onGenreClicked = {genre ->
                                    navController.navigate(Screens.GenreScreen.name+"/$genre")
                                })
                        }
                    }
                }
            }
        }
    }
}