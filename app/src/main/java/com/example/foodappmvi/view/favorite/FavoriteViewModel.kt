package com.example.foodappmvi.view.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappmvi.data.repository.FavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favRepository: FavRepository): ViewModel() {
    val favIntent = Channel<FavIntent>()

    private val _state = MutableStateFlow<FavState>(FavState.Empty)
    val state: StateFlow<FavState> get() = _state

    init {
        handelIntent()
    }

    private fun handelIntent() = viewModelScope.launch {
        favIntent.consumeAsFlow().collect {
            when(it) {
                is FavIntent.ShowAllFoodIntent -> { fetchingAllFoodShow() }
            }
        }
    }

    private fun fetchingAllFoodShow() = viewModelScope.launch {
        try{
            favRepository.showAllFood().collect {
                if (it.isEmpty()){
                    _state.emit(FavState.Empty)
                }else {
                    _state.emit(FavState.ShowAllFood(it))
                }
            }
        }catch (e: Exception) {
            _state.emit(FavState.Empty)
        }
    }


}