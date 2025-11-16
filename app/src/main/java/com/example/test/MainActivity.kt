package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                AppDestinations.SEARCH -> SearchScreen(Modifier.padding(innerPadding))
                AppDestinations.MAIN -> MainScreen(Modifier.padding(innerPadding))
                AppDestinations.SETTINGS -> SettingsScreen(Modifier.padding(innerPadding))
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
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
) {
    val message = viewModel.message.value

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(text = message)

        Spacer(modifier = Modifier.width(10.dp))

        Button(onClick = { viewModel.search() }) {
            Text("Search")
        }
    }
}
sealed class MainRoutes(val route: String) {
    data object First : MainRoutes("sub1")
    data object Second : MainRoutes("sub2")
}
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainRoutes.First.route,
        modifier = modifier
    ) {
        composable(MainRoutes.First.route) {
            FirstSubScreen(
                onNext = { navController.navigate(MainRoutes.Second.route) }
            )
        }

        composable(MainRoutes.Second.route) {
            SecondSubScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
@Composable
fun FirstSubScreen(onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Це перший підекран")

        Button(onClick = onNext, modifier = Modifier.padding(top = 16.dp)) {
            Text("Перейти на другий")
        }
    }
}
@Composable
fun SecondSubScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Другий підекран", modifier = Modifier.align(Alignment.CenterHorizontally))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    val status = viewModel.status.value
    Column(
        modifier = modifier.fillMaxSize().
        padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = status,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = { viewModel.updateStatus() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save changes")
        }
    }
}

