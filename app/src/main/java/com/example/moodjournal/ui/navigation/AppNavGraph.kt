package com.example.moodjournal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodjournal.ui.onboarding.GoalsScreen
import com.example.moodjournal.ui.onboarding.QuizScreen
import com.example.moodjournal.ui.onboarding.ReportScreen
import com.example.moodjournal.ui.onboarding.SignupScreen
import com.example.moodjournal.ui.onboarding.SplashScreen
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: MoodJournalViewModel = viewModel()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onNext = { navController.navigate("signup") })
        }
        composable("signup") {
            SignupScreen(onNext = { navController.navigate("goals") })
        }
        composable("goals") {
            GoalsScreen(viewModel = viewModel, onNext = { navController.navigate("quiz") })
        }
        composable("quiz") {
            QuizScreen(viewModel = viewModel, onNext = { navController.navigate("report")})
        }
        composable("report") {
            ReportScreen(viewModel = viewModel, onNext = { navController.navigate("home")})
        }
    }
}