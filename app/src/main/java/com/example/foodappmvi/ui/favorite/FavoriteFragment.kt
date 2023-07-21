package com.example.foodappmvi.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodappmvi.R
import com.example.foodappmvi.databinding.FragmentFavoriteBinding
import com.example.foodappmvi.ui.favorite.adapter.AdapterFav
import com.example.foodappmvi.utils.isSetAction
import com.example.foodappmvi.utils.isVisibleView
import com.example.foodappmvi.view.favorite.FavIntent
import com.example.foodappmvi.view.favorite.FavState
import com.example.foodappmvi.view.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    @Inject
    lateinit var adapterFav: AdapterFav

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main()
    }

    private fun main() {

        binding.apply {

            lifecycleScope.launch {
                favoriteViewModel.favIntent.send(FavIntent.ShowAllFoodIntent)

                favoriteViewModel.state.collect {state ->
                    when(state) {
                        is FavState.ShowAllFood -> {
                            layoutEmptyList.isVisibleView(false, listItemFavorite)

                            adapterFav.setDataAdapter(state.foods)

                            listItemFavorite.isSetAction(adapterFav, LinearLayoutManager(requireContext()))

                            adapterFav.clickItems {
                                val directions = FavoriteFragmentDirections.actionToDetailFragment(it.id)
                                findNavController().navigate(directions)
                            }
                        }
                        is FavState.Empty -> {
                            layoutEmptyList.isVisibleView(true, listItemFavorite)
                            includedEmptyItems.imgEmptyOrInternet.setImageResource(R.drawable.empty)
                        }
                    }
                }
            }

        }

    }

}