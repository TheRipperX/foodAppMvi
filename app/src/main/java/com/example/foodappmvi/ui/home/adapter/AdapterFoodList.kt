package com.example.foodappmvi.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodappmvi.data.model.server.ResponseFoodList
import com.example.foodappmvi.databinding.ItemFoodListBinding
import com.example.foodappmvi.utils.DifferAdapterEx
import javax.inject.Inject

class AdapterFoodList @Inject constructor() : RecyclerView.Adapter<AdapterFoodList.ViewHolderFoodList>() {


    private lateinit var binding: ItemFoodListBinding

    //private var isSelect = -1

    private var oldList = emptyList<ResponseFoodList.Meal>()

    inner class ViewHolderFoodList : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun getData(item: ResponseFoodList.Meal) {
            binding.apply {
                imgFoodList.load(item.strMealThumb){
                    crossfade(true)
                    crossfade(500)
                }
                txtFoodListTitle.text = item.strMeal

                if (item.strCategory.isNullOrEmpty())
                    txtFood.isVisible = false
                else{
                    txtFood.isVisible = true
                    txtFood.text = item.strCategory
                }


                if (item.strArea.isNullOrEmpty())
                    txtFoodArea.isVisible = false
                else{
                    txtFoodArea.isVisible = true
                    txtFoodArea.text = item.strArea
                }

                txtFoodSite.isVisible = !item.strSource.isNullOrEmpty()

                root.setOnClickListener {
                    setClick?.let { click->
                        click(item)
                    }
                }
            }

        }
    }

    private var setClick: ((ResponseFoodList.Meal) -> Unit)? = null

    fun clickItems(listener: (ResponseFoodList.Meal) -> Unit) {
        setClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFoodList {
        binding = ItemFoodListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderFoodList()
    }


    override fun getItemCount(): Int = oldList.size


    override fun onBindViewHolder(holder: ViewHolderFoodList, position: Int) {
        holder.getData(oldList[position])
        holder.setIsRecyclable(false)
    }

    fun setDataAdapter(newList: MutableList<ResponseFoodList.Meal>) {
        val categoryDiffer = DifferAdapterEx(oldList, newList)
        val differ = DiffUtil.calculateDiff(categoryDiffer)
        oldList = newList
        differ.dispatchUpdatesTo(this)
    }

}