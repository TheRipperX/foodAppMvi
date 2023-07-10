package com.example.foodappmvi.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappmvi.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository): ViewModel(){

    val homeChannel = Channel<HomeIntent>()

    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    val state: StateFlow<HomeState> get() = _state
    init {
        handelIntent()
    }

    private fun handelIntent() = viewModelScope.launch {
        homeChannel.consumeAsFlow().collect {
            when(it){
                is HomeIntent.SetSpinnerIntent -> { fetchSpinnerList() }
                is HomeIntent.SetRandomIntent -> { fetchRandomImage() }
            }
        }
    }

    private suspend fun fetchSpinnerList() {
        val spinnerItems = listOf('A'..'Z').flatten().toMutableList()
        _state.emit(HomeState.SetSpinnerState(spinnerItems))
    }

    private suspend fun fetchRandomImage() {
        val random = homeRepository.reqRandom()
        when(random.code()) {
            in 200..202 -> {
                if (random.isSuccessful){
                    random.body()?.let {
                        _state.emit(HomeState.SetRandomState(it.meals[0]))
                    }

                }else {
                    _state.emit(HomeState.Error("no success\nplease check internet..."))
                }

            }
            in 400..499 -> {
                _state.emit(HomeState.Error("not fund\nplease try again..."))
            }
            in 500..599 -> {
                _state.emit(HomeState.Error("server error\nthe connect server is error please try again..."))
            }
        }
    }
}