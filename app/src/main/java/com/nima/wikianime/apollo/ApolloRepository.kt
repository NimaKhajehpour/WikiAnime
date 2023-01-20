package com.nima.wikianime.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.network.NetworkTransport
import com.nima.wikianime.*
import com.nima.wikianime.type.MediaFormat
import com.nima.wikianime.type.MediaSeason
import com.nima.wikianime.type.MediaSort
import java.util.OptionalInt

class ApolloRepository{
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://graphql.anilist.co/").build()


    suspend fun getAllGenres(): ApolloResponse<AllGenresQuery.Data> {
        return apolloClient.query(AllGenresQuery()).execute()
    }

    suspend fun getTrendingNow(page: Int, perPage: Int): ApolloResponse<TrendingNowQuery.Data>{
        return apolloClient.query(TrendingNowQuery(page = Optional.Present(page),
            perPage = Optional.Present(perPage))).execute()
    }

    suspend fun getPopularAllTime(page: Int, perPage: Int): ApolloResponse<PopularAllTimeQuery.Data>{
        return apolloClient.query(PopularAllTimeQuery(
            page = Optional.Present(page),
            perPage = Optional.Present(perPage)
        )).execute()
    }

    suspend fun getTopHundred(page: Int, perPage: Int): ApolloResponse<TopHundredQuery.Data>{
        return apolloClient.query(TopHundredQuery(
            page = Optional.Present(page),
            perPage = Optional.Present(perPage)
        )).execute()
    }

    suspend fun getAllGenreMedias(page: Int, genre: String): ApolloResponse<AllGenreMediasQuery.Data>{
        return apolloClient.query(AllGenreMediasQuery(
            page = Optional.Present(page),
            genre = Optional.Present(genre)
        )).execute()
    }

    suspend fun getSearchResult(page: Int, search: String?,
                                genre: String?, seasonYear: Int?,
                                season: MediaSeason?, format: MediaFormat?,
                                sort: List<MediaSort>?
    ): ApolloResponse<SearchQuery.Data>{
        return apolloClient.query(SearchQuery(
            page = Optional.Present(page),
            search = Optional.Present(search),
            genre = Optional.Present(genre),
            seasonYear = Optional.Present(seasonYear),
            season = Optional.Present(season),
            format = Optional.Present(format),
            sort = Optional.Present(sort)
        )).execute()
    }

    suspend fun getMediaInfo(mediaId: Int): ApolloResponse<MediaPageQuery.Data>{
        return apolloClient.query(MediaPageQuery(
            mediaId = Optional.Present(mediaId)
        )).execute()
    }
}