package com.example.stocks.presentation.ui.utility

sealed class InvestedUiState<out T> {
    object Loading : InvestedUiState<Nothing>()
    data class Success<out T>(val data: T) : InvestedUiState<T>()
    data class Error(val message: String) : InvestedUiState<Nothing>()
}