package com.nima.wikianime.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.wikianime.apollo.ApolloRepository

class HomeViewModel(private val repository: ApolloRepository = ApolloRepository()): ViewModel() {

    suspend fun getAllGenres() = repository.getAllGenres()
    suspend fun getTrendingNow() = repository.getTrendingNow(1, 10)
    suspend fun getPopularAllTime() = repository.getPopularAllTime(1, 10)
    suspend fun getTopTen() = repository.getTopHundred(1, 10)
}