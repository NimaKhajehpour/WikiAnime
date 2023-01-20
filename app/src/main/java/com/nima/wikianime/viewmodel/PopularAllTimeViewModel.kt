package com.nima.wikianime.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.wikianime.apollo.ApolloRepository

class PopularAllTimeViewModel(private val repository: ApolloRepository = ApolloRepository())
    :ViewModel(){

        suspend fun getPopularAllTime(page: Int) = repository.getPopularAllTime(page, 30)
}