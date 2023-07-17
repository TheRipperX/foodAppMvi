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
                is HomeIntent.SetCategoryIntent -> { fetchCategory() }
                is HomeIntent.SetFoodListIntent -> { fetchFoodList(it.food) }
                is HomeIntent.SetSearchFoodIntent -> { fetchSearchFood(it.search) }
                is HomeIntent.SetCategoryList -> { fetchCategoryList(it.category) }
            }
        }
    }

    private fun fetchCategoryList(category: String) = viewModelScope.launch {
        val request = homeRepository.reqCategoryList(category)

        _state.emit(HomeState.SetLoadingFoodList)
        when(request.code()) {

            in 200..202 -> {

                request.body()?.let {

                    if (!it.meals.isNullOrEmpty())
                        _state.emit(HomeState.SetFoodListState(it.meals.toMutableList()))
                    else
                        _state.emit(HomeState.Empty)

                }
            }
            in 400..499 -> { _state.emit(HomeState.Error("not fund\nplease try again...")) }
            in 500..599 -> { _state.emit(HomeState.Error("server error\nthe connect server is error please try again...")) }
        }
    }

    private fun fetchSearchFood(search: String) = viewModelScope.launch {
        val request = homeRepository.reqSearchFood(search)
        _state.emit(HomeState.SetLoadingFoodList)
        when(request.code()) {

            in 200..202 -> {

                request.body()?.let {

                    if (!it.meals.isNullOrEmpty())
                        _state.emit(HomeState.SetFoodListState(it.meals.toMutableList()))
                    else
                        _state.emit(HomeState.Empty)

                }
            }
            in 400..499 -> { _state.emit(HomeState.Error("not fund\nplease try again...")) }
            in 500..599 -> { _state.emit(HomeState.Error("server error\nthe connect server is error please try again...")) }
        }
    }

    private fun fetchFoodList(food: String) = viewModelScope.launch{
        val request = homeRepository.reqFoodList(food)

        _state.emit(HomeState.SetLoadingFoodList)
        when(request.code()) {

            in 200..202 -> {

                request.body()?.let {

                    if (!it.meals.isNullOrEmpty())
                        _state.emit(HomeState.SetFoodListState(it.meals.toMutableList()))
                    else
                        _state.emit(HomeState.Empty)

                }
            }
            in 400..499 -> { _state.emit(HomeState.Error("not fund\nplease try again...")) }
            in 500..599 -> { _state.emit(HomeState.Error("server error\nthe connect server is error please try again...")) }
        }

    }

    private fun fetchCategory() = viewModelScope.launch {
        val category = homeRepository.reqCategory()

        _state.emit(HomeState.SetLoadingCategory)
        when(category.code()) {
            in 200..202 -> {
                if (category.isSuccessful){
                    category.body()?.let {
                        _state.emit(HomeState.SetCategoryState(it.categories.toMutableList()))
                    }

                }else { _state.emit(HomeState.Error("unsuccessful\nno data is show...")) }
            }
            in 400..499 -> { _state.emit(HomeState.Error("not fund\nplease try again...")) }
            in 500..599 -> { _state.emit(HomeState.Error("server error\nthe connect server is error please try again...")) }
        }
    }

    private fun fetchSpinnerList()  = viewModelScope.launch {
        val spinnerItems = listOf('A'..'Z').flatten().toMutableList()
        _state.emit(HomeState.SetSpinnerState(spinnerItems))
    }

    private fun fetchRandomImage()  = viewModelScope.launch {
        val random = homeRepository.reqRandom()
        when(random.code()) {
            in 200..202 -> {
                if (random.isSuccessful){
                    random.body()?.let {
                        _state.emit(HomeState.SetRandomState(it.meals[0]))
                    }

                }else { _state.emit(HomeState.Error("no success\nplease check internet...")) }
            }
            in 400..499 -> { _state.emit(HomeState.Error("not fund\nplease try again...")) }
            in 500..599 -> { _state.emit(HomeState.Error("server error\nthe connect server is error please try again...")) }
        }
    }
}