package com.nima.wikianime.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nima.wikianime.screens.*
import com.nima.wikianime.viewmodel.*

@Composable
fun WikiAnimeNavigation (){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.name){
        composable(Screens.HomeScreen.name){
            HomeScreen(navController = navController, viewModel = viewModel(HomeViewModel::class.java))
        }
        composable(Screens.TrendingScreen.name){
            TrendingScreen(navController = navController, viewModel = viewModel(TrendingViewModel::class.java))
        }
        composable(Screens.GenreScreen.name+"/{genre}",
            arguments = listOf(
                navArgument(name = "genre"){type = NavType.StringType}
            )
        ){
            GenreScreen(navController = navController,
                viewModel = viewModel(GenreViewModel::class.java),
                genre = it.arguments?.getString("genre"))
        }
        composable(Screens.PopularScreen.name){
            PopularAllTimeScreen(navController = navController,
                viewModel = viewModel(PopularAllTimeViewModel::class.java))
        }
        composable(Screens.TopAnimeScreen.name){
            TopAnimeScreen(navController = navController,
                viewModel = viewModel(TopAnimeViewModel::class.java))
        }
        composable(Screens.SearchScreen.name){
            SearchScreen(navController = navController, viewModel = viewModel(SearchViewModel::class.java))
        }

        composable(Screens.MediaScreen.name+"/{id}",
            arguments = listOf(
                navArgument(name = "id"){type = NavType.IntType}
            )
        ){
            MediaScreen(navController = navController, viewModel = viewModel(MediaViewModel::class.java),
                mediaId = it.arguments?.getInt("id"))
        }
    }
}