package com.nima.wikianime.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.wikianime.apollo.ApolloRepository

class TopAnimeViewModel(private val repository: ApolloRepository = ApolloRepository())
    :ViewModel(){

        suspend fun getTopAnime(page: Int) = repository.getTopHundred(page, 30)
}