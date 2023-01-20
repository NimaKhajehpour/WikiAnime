package com.nima.wikianime.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.wikianime.apollo.ApolloRepository
import com.nima.wikianime.type.MediaFormat
import com.nima.wikianime.type.MediaSeason
import com.nima.wikianime.type.MediaSort

class SearchViewModel(private val repository: ApolloRepository = ApolloRepository())
    :ViewModel(){

        suspend fun getSearchResult(
            page: Int, search: String? = null,
            genre: String? = null, seasonYear: Int? = null,
            season: MediaSeason? = null, format: MediaFormat? = null,
            sort: List<MediaSort>? = null
        ) = repository.getSearchResult(
            page,
            search,
            genre,
            seasonYear,
            season,
            format,
            sort
        )

    suspend fun getAllGenres() = repository.getAllGenres()
}