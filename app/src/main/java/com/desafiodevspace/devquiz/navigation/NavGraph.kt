package com.desafiodevspace.devquiz.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.desafiodevspace.devquiz.ui.screen.HomeScreen
import com.desafiodevspace.devquiz.ui.screen.QuizScreen
import com.desafiodevspace.devquiz.ui.screen.ResultScreen


@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController, startDestination = "home"){
        composable("home"){ HomeScreen(navController) }

        composable("quiz"){ QuizScreen(navController) }

        composable(
            route = "result?score={score}",
            arguments = listOf(
                navArgument("score") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0

            val totalQuestions = 10


            ResultScreen(
                navController = navController,
                score = score,
                totalQuestions = totalQuestions
            )
        }
    }
}