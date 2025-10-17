package com.desafiodevspace.devquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.desafiodevspace.devquiz.data.BottomNavItem
import com.desafiodevspace.devquiz.navigation.NavGraph
import com.desafiodevspace.devquiz.ui.theme.DevQuizTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        var keepSplash = true
        splashScreen.setKeepOnScreenCondition { keepSplash }
        enableEdgeToEdge()
        lifecycleScope.launch {
            delay(7000)
            keepSplash = false
        }
        super.onCreate(savedInstanceState)
        setContent {
            DevQuizApp()
        }
    }
}

@Composable
fun DevQuizApp() {
    DevQuizTheme {
        val navController = rememberNavController()
        val items = listOf(
            BottomNavItem("Início", "home", Icons.Default.Home),
            BottomNavItem("Quiz", "quiz", Icons.Default.Quiz),
            BottomNavItem("Resultados", "result", Icons.Default.EmojiEvents)
        )

        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavGraph(navController = navController, paddingValues = innerPadding)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DevQuizAppPreview() {
    DevQuizApp()
}
