package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.compose.rememberNavController
import com.example.test.screens.MainScreen
import com.example.test.screens.SearchScreen
import com.example.test.screens.SettingsScreen
import com.example.test.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTheme {
                TestApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun TestApp() {
    val mainNav = rememberNavController()

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.MAIN) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = { Icon(it.icon, contentDescription = it.label) },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.SEARCH ->
                    SearchScreen(Modifier.padding(innerPadding))

                AppDestinations.MAIN ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        nav = mainNav
                    )

                AppDestinations.SETTINGS ->
                    SettingsScreen(Modifier.padding(innerPadding))
            }
        }
    }
}


enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    SEARCH("Search", Icons.Default.Search),
    MAIN("Main", Icons.Default.Home),
    SETTINGS("Settings", Icons.Default.AccountBox),
}

sealed class MainRoutes(val route: String) {
    data object Start : MainRoutes("start")
    data object First : MainRoutes("first")
    data object Second : MainRoutes("second")
}