package com.nima.wikianime.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.wikianime.apollo.ApolloRepository

class TrendingViewModel(private val repository: ApolloRepository = ApolloRepository())
    : ViewModel(){

        suspend fun getTrendingNow(page: Int) = repository.getTrendingNow(page = page, perPage = 30)
}