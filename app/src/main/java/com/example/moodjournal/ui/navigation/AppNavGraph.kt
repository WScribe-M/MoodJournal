package com.example.moodjournal.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moodjournal.ui.history.HistoryScreen
import com.example.moodjournal.ui.home.AIResponseScreen
import com.example.moodjournal.ui.home.HomeScreen
import com.example.moodjournal.ui.onboarding.CheckinScreen
import com.example.moodjournal.ui.onboarding.GoalsScreen
import com.example.moodjournal.ui.onboarding.QuizScreen
import com.example.moodjournal.ui.onboarding.ReportScreen
import com.example.moodjournal.ui.onboarding.SignupScreen
import com.example.moodjournal.ui.onboarding.SplashScreen
import com.example.moodjournal.ui.onboarding.ToolsScreen
import com.example.moodjournal.ui.tools.BreathingScreen
import com.example.moodjournal.ui.tools.GratitudeScreen
import com.example.moodjournal.viewmodel.MoodJournalViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: MoodJournalViewModel = viewModel()

    // Récupère la route actuelle pour savoir si on doit afficher la TabBar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showTabBar = currentRoute in listOf("home", "history", "tools", "profile")

    Scaffold(
        bottomBar = {
            if (showTabBar) {
                BottomTabBar(navController = navController, currentRoute = currentRoute)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(padding)
        ) {
            composable("splash") {
                SplashScreen(onNext = { navController.navigate("signup") })
            }
            composable("signup") {
                SignupScreen(viewModel = viewModel, onNext = { navController.navigate("goals") })
            }
            composable("goals") {
                GoalsScreen(viewModel = viewModel, onNext = { navController.navigate("quiz") })
            }
            composable("quiz") {
                QuizScreen(viewModel = viewModel, onNext = { navController.navigate("report") })
            }
            composable("report") {
                ReportScreen(viewModel = viewModel, onNext = { navController.navigate("home") })
            }
            composable("home") {
                HomeScreen(viewModel = viewModel, onNext = { navController.navigate("checkin") })
            }
            composable("checkin") {
                CheckinScreen(
                    viewModel = viewModel,
                    onNext = { navController.navigate("aiResponse") })
            }
            composable("aiResponse") {
                AIResponseScreen(
                    viewModel = viewModel,
                    onNext = { navController.navigate("history") })
            }
            composable("history") {
                HistoryScreen(viewModel = viewModel, onNext = { navController.navigate("rien") })
            }
            composable("breathing") {
                BreathingScreen(onClose = { navController.popBackStack() })
            }
            composable("gratitude") {
                GratitudeScreen(
                    viewModel = viewModel,
                    onClose = { navController.popBackStack() }
                )
            }
            composable("tools") {
                ToolsScreen(onTool = { tool -> navController.navigate(tool) })
            }
            composable("profile") { Text("Profil — à faire") }
            composable("signup") {
                SignupScreen(viewModel = viewModel, onNext = { navController.navigate("goals") })
            }
        }
    }
}

@Composable
fun BottomTabBar(navController: NavHostController, currentRoute: String?) {
    val tabs = listOf(
        Triple("home", "Accueil", Icons.Default.Home),
        Triple("history", "Historique", Icons.Default.DateRange),
        Triple("tools", "Outils", Icons.Default.Build),
        Triple("profile", "Profil", Icons.Default.Person)
    )

    NavigationBar {
        tabs.forEach { (route, label, icon) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) }
            )
        }
    }
}