package com.example.stocks.presentation.ui.utility

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val img: ImageVector) {
    object WatchList : Screen("watchlist", "Watchlist", Icons.AutoMirrored.Filled.List)
    object Orders : Screen("orders", "Orders", Icons.Filled.ShoppingCart)
    object Portfolio : Screen("portfolio", "Portfolio", Icons.Default.AccountCircle)
    object Funds : Screen("funds", "Funds", Icons.Default.ThumbUp)
    object Invest : Screen("invest", "Invest", Icons.Default.Favorite)
}