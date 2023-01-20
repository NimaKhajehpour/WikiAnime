package com.nima.wikianime.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.wikianime.apollo.ApolloRepository

class GenreViewModel(private val repository: ApolloRepository = ApolloRepository())
    :ViewModel(){

        suspend fun getAllGenreMedias(page: Int, genre: String) = repository.getAllGenreMedias(page, genre)
}