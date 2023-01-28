package com.nima.wikianime.components

import android.graphics.Color.parseColor
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.nima.wikianime.MediaPageQuery
import com.nima.wikianime.PopularAllTimeQuery
import com.nima.wikianime.TopHundredQuery
import com.nima.wikianime.TrendingNowQuery

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThumbnailItem(
    media: TrendingNowQuery.Medium,
    onClick: (Int) -> Unit,
    ) {

    val color = if (media.coverImage?.color != null )
        parseColor(media.coverImage.color)
        else parseColor("#000000")

    val compColor = remember {
        Integer.toHexString(parseColor("#ffffff") - color)
    }

    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(

        ){
            ElevatedCard(
                onClick = {
                    // go to page
                    onClick(media.id)
                },
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(10.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(12.dp)
            ) {
                SubcomposeAsyncImage(
                    model = media.coverImage?.extraLarge,
                    contentDescription = null,
                    success = {
                        SubcomposeAsyncImageContent()
                    },
                    loading = {
                        Surface(
                            color = Color(color),
                            modifier = Modifier.size(160.dp, 200.dp)
                        ){

                        }
                    },
                    error = {
                        Surface(
                            color = Color(color),
                            modifier = Modifier.size(160.dp, 200.dp)
                        ){

                        }
                    }

                )
            }
            if (media.averageScore != null){
                ElevatedCard(
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.TopStart),
                    elevation = CardDefaults.elevatedCardElevation(
                        5.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color(
                            parseColor(
                            when(compColor.length){
                                5 -> "#0$compColor"
                                4 -> "#00$compColor"
                                3 -> "#000$compColor"
                                2 -> "#0000$compColor"
                                1 -> "#00000$compColor"
                                0 -> "#000000$compColor"
                                else -> "#$compColor"
                            }
                            )
                        ),
                        containerColor = Color(color)
                    )
                ) {
                    Text(
                        text = "%${media.averageScore}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
                    )
                }
            }
            if (!media.format?.name.isNullOrEmpty()){
                ElevatedCard(
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    elevation = CardDefaults.elevatedCardElevation(
                        5.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color(
                            parseColor(
                                when(compColor.length){
                                    5 -> "#0$compColor"
                                    4 -> "#00$compColor"
                                    3 -> "#000$compColor"
                                    2 -> "#0000$compColor"
                                    1 -> "#00000$compColor"
                                    0 -> "#000000$compColor"
                                    else -> "#$compColor"
                                }
                            )
                        ),
                        containerColor = Color(color)
                    )
                ) {
                    Text(
                        text = "${media.format?.name?.replace("_", " ")}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
        Text(
            text = if (media.title?.userPreferred?.length!! < 21)
                media.title.userPreferred else media.title.userPreferred.substring(
                0,
                17
            ) + "...",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 5.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThumbnailItem(
    media: PopularAllTimeQuery.Medium,
    onClick: (Int) -> Unit,
    ) {

    val color = if (media.coverImage?.color != null )
        parseColor(media.coverImage.color)
        else parseColor("#000000")

    val compColor = remember {
        Integer.toHexString(parseColor("#ffffff") - color)
    }

    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(

        ){
            ElevatedCard(
                onClick = {
                    // go to page
                    onClick(media.id)
                },
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(10.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(12.dp)
            ) {
                SubcomposeAsyncImage(
                    model = media.coverImage?.extraLarge,
                    contentDescription = null,
                    success = {
                        SubcomposeAsyncImageContent()
                    },
                    loading = {
                        Surface(
                            color = Color(color),
                            modifier = Modifier.size(160.dp, 200.dp)
                        ){

                        }
                    },
                    error = {
                        Surface(
                            color = Color(color),
                            modifier = Modifier.size(160.dp, 200.dp)
                        ){

                        }
                    }

                )
            }
            if (media.averageScore != null){
                ElevatedCard(
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.TopStart),
                    elevation = CardDefaults.elevatedCardElevation(
                        5.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color(
                            parseColor(
                                when(compColor.length){
                                    5 -> "#0$compColor"
                                    4 -> "#00$compColor"
                                    3 -> "#000$compColor"
                                    2 -> "#0000$compColor"
                                    1 -> "#00000$compColor"
                                    0 -> "#000000$compColor"
                                    else -> "#$compColor"
                                }
                            )
                        ),
                        containerColor = Color(color)
                    )
                ) {
                    Text(
                        text = "%${media.averageScore}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
                    )
                }
            }
            if (!media.format?.name.isNullOrEmpty()){
                ElevatedCard(
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    elevation = CardDefaults.elevatedCardElevation(
                        5.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color(
                            parseColor(
                                when(compColor.length){
                                    5 -> "#0$compColor"
                                    4 -> "#00$compColor"
                                    3 -> "#000$compColor"
                                    2 -> "#0000$compColor"
                                    1 -> "#00000$compColor"
                                    0 -> "#000000$compColor"
                                    else -> "#$compColor"
                                }
                            )
                        ),
                        containerColor = Color(color)
                    )
                ) {
                    Text(
                        text = "${media.format?.name?.replace("_", " ")}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
        Text(
            text = if (media.title?.userPreferred?.length!! < 21)
                media.title.userPreferred else media.title.userPreferred.substring(
                0,
                17
            ) + "...",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 5.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThumbnailItem(
    media: TopHundredQuery.Medium,
    onClick: (Int) -> Unit,
    ) {

    val color = if (media.coverImage?.color != null )
        parseColor(media.coverImage.color)
    else parseColor("#000000")

    val compColor = remember {
        Integer.toHexString(parseColor("#ffffff") - color)
    }

    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(

        ){
            ElevatedCard(
                onClick = {
                    // go to page
                    onClick(media.id)
                },
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(10.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(12.dp)
            ) {
                SubcomposeAsyncImage(
                    model = media.coverImage?.extraLarge,
                    contentDescription = null,
                    success = {
                        SubcomposeAsyncImageContent()
                    },
                    loading = {
                        Surface(
                            color = Color(color),
                            modifier = Modifier.size(160.dp, 200.dp)
                        ){

                        }
                    },
                    error = {
                        Surface(
                            color = Color(color),
                            modifier = Modifier.size(160.dp, 200.dp)
                        ){

                        }
                    }

                )
            }
            if (media.averageScore != null){
                ElevatedCard(
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.TopStart),
                    elevation = CardDefaults.elevatedCardElevation(
                        5.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color(
                            parseColor(
                                when(compColor.length){
                                    5 -> "#0$compColor"
                                    4 -> "#00$compColor"
                                    3 -> "#000$compColor"
                                    2 -> "#0000$compColor"
                                    1 -> "#00000$compColor"
                                    0 -> "#000000$compColor"
                                    else -> "#$compColor"
                                }
                            )
                        ),
                        containerColor = Color(color)
                    )
                ) {
                    Text(
                        text = "%${media.averageScore}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
                    )
                }
            }
            if (!media.format?.name.isNullOrEmpty()){
                ElevatedCard(
                    shape = CircleShape,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    elevation = CardDefaults.elevatedCardElevation(
                        5.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color(
                            parseColor(
                                when(compColor.length){
                                    5 -> "#0$compColor"
                                    4 -> "#00$compColor"
                                    3 -> "#000$compColor"
                                    2 -> "#0000$compColor"
                                    1 -> "#00000$compColor"
                                    0 -> "#000000$compColor"
                                    else -> "#$compColor"
                                }
                            )
                        ),
                        containerColor = Color(color)
                    )
                ) {
                    Text(
                        text = "${media.format?.name?.replace("_", " ")}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
        Text(
            text = if (media.title?.userPreferred?.length!! < 21)
                media.title.userPreferred else media.title.userPreferred.substring(
                0,
                17
            ) + "...",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 5.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}