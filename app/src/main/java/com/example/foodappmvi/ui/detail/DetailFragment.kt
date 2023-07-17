package com.example.foodappmvi.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.foodappmvi.databinding.FragmentDetailBinding
import com.example.foodappmvi.view.detail.DetailIntent
import com.example.foodappmvi.view.detail.DetailState
import com.example.foodappmvi.view.detail.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()
    private var foodId = 0

    @Inject
    lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main()
    }

    private fun main() {
        foodId = args.idFood
        binding.apply {

            //back btn
            imgBack.setOnClickListener { findNavController().popBackStack() }
            //set data
            lifecycleScope.launch {
                detailViewModel.detailChannel.send(DetailIntent.SetDetailIntent(foodId))

                //set data to view
                detailViewModel.state.collect {state->
                    when(state) {

                        is DetailState.Idle -> {}

                        is DetailState.SetDetails -> {
                            progressDetail.isVisible = false

                            val detail = state.list[0]

                            //set image
                            imgFood.load(detail.strMealThumb){
                                crossfade(true)
                                crossfade(500)
                            }
                            //set name
                            imgModeFood.text = detail.strCategory
                            //set area
                            imgAreaFood.text = detail.strArea
                            //set page
                            try{
                                if (detail.strSource.toString().isNullOrEmpty()){
                                    imgPageFood.isVisible = false
                                }else{
                                    imgPageFood.isVisible = true
                                    imgPageFood.setOnClickListener {
                                        val intentPage = Intent(Intent.ACTION_VIEW, Uri.parse(detail.strSource.toString()))
                                        startActivity(intentPage)
                                    }
                                }
                            }catch (e: Exception) {
                                imgPageFood.isVisible = false
                            }
                            //set title
                            txtTitle.text = detail.strMeal
                            //set desc
                            txtDesc.text = detail.strInstructions

                            //set other
                            val json = JSONObject(Gson().toJson(detail))

                            for(i in 1..15) {

                                val strIngredient = json.getString("strIngredient$i")
                                if (!strIngredient.isNullOrEmpty())
                                    txtInDesc.append("$strIngredient\n")

                                val strMeasure = json.getString("strMeasure$i")
                                if (!strIngredient.isNullOrEmpty())
                                    txtMeasDesc.append("$strMeasure\n")
                            }

                        }

                        is DetailState.SetDetailLoading -> {
                            progressDetail.isVisible = true
                        }

                        is DetailState.Error -> {
                            progressDetail.isVisible = false
                            Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }


        }
    }

}