package com.example.stocks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocks.domain.model.UserHolding
import com.example.stocks.domain.usecase.GetInvestedUseCase
import com.example.stocks.presentation.ui.utility.InvestedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestedViewModel @Inject constructor(private val getInvestedUseCase: GetInvestedUseCase) :
    ViewModel() {
    private val _invested =
        MutableStateFlow<InvestedUiState<List<UserHolding>>>(InvestedUiState.Loading)
    val invested: StateFlow<InvestedUiState<List<UserHolding>>> = _invested

    val investedUiState: StateFlow<InvestedUiState<List<UserHolding>>> =
        getInvestedUseCase().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            InvestedUiState.Loading
        )

    init {
        viewModelScope.launch {
            getInvestedUseCase().collect {
                _invested.value = it
            }
        }
    }

}
