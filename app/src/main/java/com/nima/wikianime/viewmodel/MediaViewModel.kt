package com.nima.wikianime.viewmodel

import androidx.lifecycle.ViewModel
import com.nima.wikianime.apollo.ApolloRepository

class MediaViewModel(private val repository: ApolloRepository = ApolloRepository())
    :ViewModel(){

        suspend fun getMediaInfo(mediaId: Int) = repository.getMediaInfo(mediaId)
}