package com.example.foodappmvi.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappmvi.data.repository.DetailRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository): ViewModel(){

    val detailChannel = Channel<DetailIntent>()

    private val _state = MutableStateFlow<DetailState>(DetailState.Idle)
    val state: StateFlow<DetailState> get() = _state

    init {
        detailHandel()
    }

    private fun detailHandel() = viewModelScope.launch {
        detailChannel.consumeAsFlow().collect {
            when(it) {
                is DetailIntent.SetDetailIntent -> { fetchDetailList(it.id) }
            }
        }
    }

    private fun fetchDetailList(id: Int) = viewModelScope.launch{
        val request = detailRepository.reqDetail(id)

        _state.emit(DetailState.SetDetailLoading)

        when(request.code()) {
            in 200..202 -> {
                request.body()?.let {
                    if (!it.meals.isNullOrEmpty()) {
                        _state.emit(DetailState.SetDetails(it.meals.toMutableList()))
                    }else {
                        _state.emit(DetailState.Error("not fund\nplease try again..."))
                    }
                }
            }
            in 400..499 -> { _state.emit(DetailState.Error("not fund\nplease try again...")) }
            in 500..599 -> { _state.emit(DetailState.Error("server error\nthe connect server is error please try again...")) }
        }
    }
}
