package com.example.foodappmvi.ui.favorite.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodappmvi.data.db.FoodEntity
import com.example.foodappmvi.databinding.ItemFoodListBinding
import com.example.foodappmvi.utils.DifferAdapterEx
import javax.inject.Inject

class AdapterFav @Inject constructor() : RecyclerView.Adapter<AdapterFav.ViewHolderFav>(){

    private lateinit var binding: ItemFoodListBinding

    //private var isSelect = -1

    private var oldList = emptyList<FoodEntity>()

    inner class ViewHolderFav : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun getData(item: FoodEntity) {
            binding.apply {
                imgFoodList.load(item.foodImage){
                    crossfade(true)
                    crossfade(500)
                }
                txtFoodListTitle.text = item.foodName

                txtFood.isVisible = false
                txtFoodArea.isVisible = false
                txtFoodSite.isVisible = false

                root.setOnClickListener {
                    setClick?.let { click->
                        click(item)
                    }
                }
            }

        }
    }

    private var setClick: ((FoodEntity) -> Unit)? = null

    fun clickItems(listener: (FoodEntity) -> Unit) {
        setClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFav {
        binding = ItemFoodListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderFav()
    }


    override fun getItemCount(): Int = oldList.size


    override fun onBindViewHolder(holder: ViewHolderFav, position: Int) {
        holder.getData(oldList[position])
        holder.setIsRecyclable(false)
    }

    fun setDataAdapter(newList: MutableList<FoodEntity>) {
        val categoryDiffer = DifferAdapterEx(oldList, newList)
        val differ = DiffUtil.calculateDiff(categoryDiffer)
        oldList = newList
        differ.dispatchUpdatesTo(this)
    }
}