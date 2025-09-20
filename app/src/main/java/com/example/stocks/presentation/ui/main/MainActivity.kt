package com.example.stocks.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stocks.R
import com.example.stocks.presentation.ui.main.ui.theme.StocksTheme
import com.example.stocks.presentation.ui.utility.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            StocksTheme {
                var selectedItem by remember { mutableIntStateOf(2) }
                val items = listOf(
                    Screen.WatchList,
                    Screen.Orders,
                    Screen.Portfolio,
                    Screen.Invest,
                    Screen.Funds
                )
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                LaunchedEffect(currentRoute) {
                    if (currentRoute != null) selectedItem =
                        items.indexOfFirst { it.route == currentRoute }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CustomToolBar(
                            title = items[selectedItem].title,
                            image = items[selectedItem].img,
                            onMenuClicked = {},
                            onSearchClicked = {},
                            onShuffledClick = {})
                    }, bottomBar = {

                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = when (item) {
                                                is Screen.WatchList -> item.img
                                                is Screen.Orders -> item.img
                                                is Screen.Portfolio -> item.img
                                                is Screen.Invest -> item.img
                                                is Screen.Funds -> item.img
                                            },
                                            contentDescription = item.title,
                                        )
                                    },
                                    label = { Text(text = item.title) },
                                    selected = selectedItem == index,
                                    onClick = {
                                        selectedItem = index
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color.Blue,
                                        unselectedIconColor = Color.Black,
                                        selectedTextColor = Color.Blue,
                                        unselectedTextColor = Color.Black
                                    )
                                )
                            }
                        }
                    }) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),

                        ) {
                        NavGraphControllerScreen(navController, Screen.Portfolio.route)
                    }

                }
            }
        }
    }
}

@Composable
fun CustomToolBar(
    title: String,
    image: ImageVector,
    onMenuClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onShuffledClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF6200EE)) // Purple toolbar
            .padding(top = 22.dp, bottom = 0.dp, start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left: Menu Icon + Title
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = image,
                    contentDescription = stringResource(id = R.string.image),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontSize = 16.sp
            )
        }

        // Right: Action Icons
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(id = R.string.image),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .background(color = Color.Gray))
            IconButton(onClick = onShuffledClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.image),
                    tint = Color.White
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StocksTheme {

    }
}
