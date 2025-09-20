package com.example.stocks.presentation.ui.utility

sealed class InvestedUiState<T> {
    class Loading<T> : InvestedUiState<T>()
    data class Success<T>(val data: T) : InvestedUiState<T>()
    data class Error<T>(val message: String) : InvestedUiState<T>()
}