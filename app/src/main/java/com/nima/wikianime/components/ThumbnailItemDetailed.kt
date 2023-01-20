package com.nima.wikianime.components

import android.graphics.Color.parseColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.nima.wikianime.*

@Composable
fun ThumbnailItemDetailed(
    media: TrendingNowQuery.Medium,
    onClick: (Int) -> Unit,
    onGenreClicked: (String) -> Unit
){

    val color = if (media.coverImage?.color != null )
        parseColor(media.coverImage.color)
    else parseColor("#000000")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.padding(2.dp)
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
        Card(
            modifier = Modifier
                .weight(1f, true)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = media.title?.userPreferred != null){
                    Text(
                        text = media.title?.userPreferred.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
                AnimatedVisibility(visible = media.format?.name != null) {
                    Text(
                        text = "Format: " + media.format?.name?.replace("_", " "),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.averageScore != null) {
                    Text(
                        text = "Average Score: %" + media.averageScore,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.episodes != null) {
                    Text(
                        text = "Episodes: " + media.episodes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = !media.studios?.nodes.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Studios:",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center

                        )
                        media.studios?.nodes?.forEach {
                            if (it != null) {
                                if (!it.name.isNullOrEmpty()){
                                    Text(text = "${ it.name }",
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center

                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = !media.genres.isNullOrEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        items(items = media.genres!!){
                            OutlinedButton(onClick = {
                                // go to genre
                                onGenreClicked(it!!)
                            },
                                modifier = Modifier.padding(horizontal = 3.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.tertiary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text(text = it!!,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = true) {
                    Button(onClick = {
                        // go to page
                        onClick(media.id)
                    },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(text = "Go To Page")
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbnailItemDetailed(
    media: MediaPageQuery.Node1,
    onClick: (Int) -> Unit,
    onGenreClicked: (String) -> Unit
){

    val color = if (media.mediaRecommendation?.coverImage?.color != null )
        parseColor(media.mediaRecommendation.coverImage.color)
    else parseColor("#000000")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.padding(2.dp)
        ) {
            SubcomposeAsyncImage(
                model = media.mediaRecommendation?.coverImage?.extraLarge,
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
        Card(
            modifier = Modifier
                .weight(1f, true)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = media.mediaRecommendation?.title?.userPreferred != null){
                    Text(
                        text = media.mediaRecommendation?.title?.userPreferred.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.mediaRecommendation?.format?.name != null) {
                    Text(
                        text = "Format: " + media.mediaRecommendation?.format?.name?.replace("_", " "),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.mediaRecommendation?.averageScore != null) {
                    Text(
                        text = "Average Score: %" + media.mediaRecommendation?.averageScore,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.mediaRecommendation?.episodes != null) {
                    Text(
                        text = "Episodes: " + media.mediaRecommendation?.episodes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = !media.mediaRecommendation?.studios?.nodes.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Studios:",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center

                        )
                        media.mediaRecommendation?.studios?.nodes?.forEach {
                            if (it != null) {
                                if (!it.name.isNullOrEmpty()){
                                    Text(text = "${ it.name }",
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center

                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = !media.mediaRecommendation?.genres.isNullOrEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        items(items = media.mediaRecommendation?.genres!!){
                            OutlinedButton(onClick = {
                                // go to genre
                                onGenreClicked(it!!)
                            },
                                modifier = Modifier.padding(horizontal = 3.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.tertiary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text(text = it!!,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = true) {
                    Button(onClick = {
                        // go to page
                        onClick(media.mediaRecommendation?.id!!)
                    },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(text = "Go To Page")
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbnailItemDetailed(
    media: PopularAllTimeQuery.Medium,
    onClick: (Int) -> Unit,
    onGenreClicked: (String) -> Unit
){

    val color = if (media.coverImage?.color != null )
        parseColor(media.coverImage.color)
    else parseColor("#000000")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.padding(2.dp)
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
        Card(
            modifier = Modifier
                .weight(1f, true)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = media.title?.userPreferred != null){
                    Text(
                        text = media.title?.userPreferred.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.format?.name != null) {
                    Text(
                        text = "Format: " + media.format?.name?.replace("_", " "),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.averageScore != null) {
                    Text(
                        text = "Average Score: %" + media.averageScore,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.episodes != null) {
                    Text(
                        text = "Episodes: " + media.episodes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = !media.studios?.nodes.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Studios:",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center

                        )
                        media.studios?.nodes?.forEach {
                            if (it != null) {
                                if (!it.name.isNullOrEmpty()){
                                    Text(text = "${ it.name }",
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center

                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = !media.genres.isNullOrEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        items(items = media.genres!!){
                            OutlinedButton(onClick = {
                                // go to genre
                                onGenreClicked(it!!)
                            },
                                modifier = Modifier.padding(horizontal = 3.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.tertiary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text(text = it!!,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = true) {
                    Button(onClick = {
                        // go to page
                        onClick(media.id)
                    },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(text = "Go To Page")
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbnailItemDetailed(
    media: TopHundredQuery.Medium,
    onClick: (Int) -> Unit,
    onGenreClicked: (String) -> Unit
){

    val color = if (media.coverImage?.color != null )
        parseColor(media.coverImage.color)
    else parseColor("#000000")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.padding(2.dp)
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
        Card(
            modifier = Modifier
                .weight(1f, true)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = media.title?.userPreferred != null){
                    Text(
                        text = media.title?.userPreferred.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.format?.name != null) {
                    Text(
                        text = "Format: " + media.format?.name?.replace("_", " "),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.averageScore != null) {
                    Text(
                        text = "Average Score: %" + media.averageScore,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.episodes != null) {
                    Text(
                        text = "Episodes: " + media.episodes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = !media.studios?.nodes.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Studios:",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center

                        )
                        media.studios?.nodes?.forEach {
                            if (it != null) {
                                if (!it.name.isNullOrEmpty()){
                                    Text(text = "${ it.name }",
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center

                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = !media.genres.isNullOrEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        items(items = media.genres!!){
                            OutlinedButton(onClick = {
                                // go to genre
                                onGenreClicked(it!!)
                            },
                                modifier = Modifier.padding(horizontal = 3.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.tertiary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text(text = it!!,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = true) {
                    Button(onClick = {
                        // go to page
                        onClick(media.id)
                    },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(text = "Go To Page")
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbnailItemDetailed(
    media: SearchQuery.Medium,
    onClick: (Int) -> Unit,
    onGenreClicked: (String) -> Unit
){

    val color = if (media.coverImage?.color != null )
        parseColor(media.coverImage.color)
    else parseColor("#000000")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.padding(2.dp)
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
        Card(
            modifier = Modifier
                .weight(1f, true)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = media.title?.userPreferred != null){
                    Text(
                        text = media.title?.userPreferred.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.format?.name != null) {
                    Text(
                        text = "Format: " + media.format?.name?.replace("_", " "),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.averageScore != null) {
                    Text(
                        text = "Average Score: %" + media.averageScore,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.episodes != null) {
                    Text(
                        text = "Episodes: " + media.episodes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = !media.studios?.nodes.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Studios:",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center

                        )
                        media.studios?.nodes?.forEach {
                            if (it != null) {
                                if (!it.name.isNullOrEmpty()){
                                    Text(text = "${ it.name }",
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center

                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = !media.genres.isNullOrEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        items(items = media.genres!!){
                            OutlinedButton(onClick = {
                                // go to genre
                                onGenreClicked(it!!)
                            },
                                modifier = Modifier.padding(horizontal = 3.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.tertiary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text(text = it!!,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = true) {
                    Button(onClick = {
                        // go to page
                        onClick(media.id)
                    },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(text = "Go To Page")
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbnailItemDetailed(
    media: AllGenreMediasQuery.Medium,
    onClick: (Int) -> Unit,
    onGenreClicked: (String) -> Unit
){

    val color = if (media.coverImage?.color != null )
        parseColor(media.coverImage.color)
    else parseColor("#000000")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.padding(2.dp)
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
        Card(
            modifier = Modifier
                .weight(1f, true)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = media.title?.userPreferred != null){
                    Text(
                        text = media.title?.userPreferred.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center,

                    )
                }
                AnimatedVisibility(visible = media.format?.name != null) {
                    Text(
                        text = "Format: " + media.format?.name?.replace("_", " "),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.averageScore != null) {
                    Text(
                        text = "Average Score: %" + media.averageScore,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = media.episodes != null) {
                    Text(
                        text = "Episodes: " + media.episodes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center

                    )
                }
                AnimatedVisibility(visible = !media.studios?.nodes.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Studios:",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center

                        )
                        media.studios?.nodes?.forEach {
                            if (it != null) {
                                if (!it.name.isNullOrEmpty()){
                                    Text(text = "${ it.name }",
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center

                                    )
                                }
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = !media.genres.isNullOrEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        items(items = media.genres!!){
                            OutlinedButton(onClick = {
                                // go to genre
                                onGenreClicked(it!!)
                            },
                                modifier = Modifier.padding(horizontal = 3.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.tertiary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text(text = it!!,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = true) {
                    Button(onClick = {
                        // go to page
                        onClick(media.id)
                    },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(text = "Go To Page")
                    }
                }
            }
        }
    }
}