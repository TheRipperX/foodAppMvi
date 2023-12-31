package com.example.foodappmvi.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.foodappmvi.databinding.FragmentHomeBinding
import com.example.foodappmvi.ui.home.adapter.AdapterCategory
import com.example.foodappmvi.ui.home.adapter.AdapterFoodList
import com.example.foodappmvi.utils.isSetAction
import com.example.foodappmvi.utils.isVisibleView
import com.example.foodappmvi.utils.network.NetworkCheck
import com.example.foodappmvi.utils.network.NetworkConnections
import com.example.foodappmvi.utils.setItemSpinner
import com.example.foodappmvi.view.home.HomeIntent
import com.example.foodappmvi.view.home.HomeState
import com.example.foodappmvi.view.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    //view model
    private val homeViewModel by viewModels<HomeViewModel>()

    //inject
    @Inject
    lateinit var categoryAdapter: AdapterCategory
    @Inject
    lateinit var adapterFoodList: AdapterFoodList
    //network check
    @Inject
    lateinit var network: NetworkConnections

    private var foodListShow = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main()
    }

    private fun main() {

        binding.apply {

            lifecycleScope.launch {
                homeViewModel.homeChannel.send(HomeIntent.SetSpinnerIntent)
                /*homeViewModel.homeChannel.send(HomeIntent.SetSpinnerIntent)
                homeViewModel.homeChannel.send(HomeIntent.SetRandomIntent)
                homeViewModel.homeChannel.send(HomeIntent.SetCategoryIntent)*/

                homeViewModel.state.collect {
                    when(it) {
                        is HomeState.Idle -> {}
                        is HomeState.SetSpinnerState -> {
                            spinnerHome.setItemSpinner(it.list) {str->
                                if (foodListShow)
                                    lifecycleScope.launch { homeViewModel.homeChannel.send(HomeIntent.SetFoodListIntent(str)) }
                            }
                        }
                        is HomeState.SetRandomState -> {
                            if (it.img != null){
                                imgRand.load(it.img.strMealThumb){
                                    crossfade(true)
                                    crossfade(500)
                                }
                                imgRand.setOnClickListener {_->
                                    val idImage = it.img.idMeal.toInt()
                                    val directions = HomeFragmentDirections.actionToDetailFragment(idImage)
                                    findNavController().navigate(directions)
                                }
                            }
                        }
                        is HomeState.Error -> {
                            progressbarCategory.isVisibleView(false, recCategoryList)
                            progressFoodList.isVisibleView(false, recFoodList)
                            Snackbar.make(binding.root, it.error, Snackbar.LENGTH_LONG).show()
                        }
                        is HomeState.SetCategoryState -> {
                            progressbarCategory.isVisibleView(false, recCategoryList)
                            val data = it.list

                            categoryAdapter.setDataAdapter(data)
                            recCategoryList.isSetAction(categoryAdapter, LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false))

                            categoryAdapter.clickItems { items ->
                                val name = items.strCategory
                                lifecycleScope.launch {
                                    homeViewModel.homeChannel.send(HomeIntent.SetCategoryList(name))
                                }

                            }
                        }
                        is HomeState.SetLoadingCategory -> {
                            progressbarCategory.isVisibleView(true, recCategoryList)
                        }
                        is HomeState.SetLoadingFoodList -> {
                            progressFoodList.isVisibleView(true, recFoodList)
                        }
                        is HomeState.SetFoodListState -> {
                            progressFoodList.isVisibleView(false, recFoodList)
                            imgNoData.isVisibleView(false, recFoodList)
                            val foodList = it.foodList

                            adapterFoodList.setDataAdapter(foodList)

                            recFoodList.isSetAction(adapterFoodList, LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false))

                            adapterFoodList.clickItems {data->
                                val id = data.idMeal.toInt()
                                val directions = HomeFragmentDirections.actionToDetailFragment(id)
                                findNavController().navigate(directions)
                            }
                        }
                        is HomeState.Empty -> {
                            progressFoodList.isVisibleView(false, recFoodList)
                            imgNoData.isVisibleView(true, recFoodList)
                        }
                    }
                }
            }

            edtSearch.addTextChangedListener {
                lifecycleScope.launch {
                    if (foodListShow)
                        homeViewModel.homeChannel.send(HomeIntent.SetSearchFoodIntent(it.toString()))
                }
            }

            //network check
            lifecycleScope.launch {
                network.observeNet().collect {
                    when(it) {
                        NetworkCheck.Status.Available -> {

                            homeViewModel.homeChannel.send(HomeIntent.SetRandomIntent)
                            homeViewModel.homeChannel.send(HomeIntent.SetCategoryIntent)
                            homeViewModel.homeChannel.send(HomeIntent.SetFoodListIntent("A"))
                            foodListShow = true
                        }
                        NetworkCheck.Status.Unavailable -> { errorNetwork() }
                        NetworkCheck.Status.Losing -> { errorNetwork() }
                        NetworkCheck.Status.Lost -> { errorNetwork() }
                    }
                }
            }

        }
    }

    private fun errorNetwork() {
        Snackbar.make(binding.root, "No internet connection available for your Android app!!", Snackbar.LENGTH_LONG).show()
        foodListShow = false
    }

}