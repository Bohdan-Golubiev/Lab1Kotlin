package com.example.test.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.test.MainRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    nav: NavHostController
) {
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val canGoBack = currentRoute != null && currentRoute != MainRoutes.Start.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentRoute ?: "") }, //Тут якщо вказувати назву баг є
                navigationIcon = {
                    if (canGoBack) {
                        IconButton(onClick = { nav.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = MainRoutes.Start.route,
            modifier = modifier.padding(padding)
        ) {
            composable(MainRoutes.Start.route) {
                MainStartScreen(onOpen = { nav.navigate(MainRoutes.First.route) })
            }
            composable(MainRoutes.First.route) {
                FirstSubScreen(onNext = { nav.navigate(MainRoutes.Second.route) })
            }
            composable(MainRoutes.Second.route) {
                SecondSubScreen(onBack = { nav.popBackStack() })
            }
        }
    }
}

@Composable
fun MainStartScreen(onOpen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Це головний екран всередині MainScreen")

        Button(
            onClick = onOpen,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Перейти в підекрани")
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
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Це другий підекран",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}