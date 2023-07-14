package com.example.foodappmvi.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foodappmvi.R
import com.example.foodappmvi.data.model.server.ResponseCategory
import com.example.foodappmvi.databinding.ItemCategoryListBinding
import com.example.foodappmvi.utils.DifferAdapterEx
import javax.inject.Inject

class AdapterCategory @Inject constructor(): RecyclerView.Adapter<AdapterCategory.ViewHolderCategory>() {

    private lateinit var binding: ItemCategoryListBinding

    private var isSelect = -1

    private var oldList = emptyList<ResponseCategory.Category>()

    inner class ViewHolderCategory: RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun getData(item: ResponseCategory.Category) {
            binding.apply {
                imgCategoryItems.load(item.strCategoryThumb){
                    crossfade(true)
                    crossfade(500)
                }
                txtCategoryItem.text = item.strCategory

                root.setOnClickListener {
                    isSelect = adapterPosition
                    notifyDataSetChanged()

                    setClick?.let {
                        it(item)
                    }
                }

                if (isSelect == adapterPosition)
                    binding.root.setBackgroundResource(R.drawable.bg_category_selected)
                else
                    binding.root.setBackgroundResource(R.drawable.items_bg_sh_category)


            }

        }
    }

    private var setClick: ((ResponseCategory.Category) -> Unit)? = null

    fun clickItems(listener: (ResponseCategory.Category) -> Unit) {
        setClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategory {
        binding = ItemCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCategory()
    }

    override fun getItemCount(): Int = oldList.size


    override fun onBindViewHolder(holder: ViewHolderCategory, position: Int) {
        holder.getData(oldList[position])
        holder.setIsRecyclable(false)
    }

    fun setDataAdapter(newList: MutableList<ResponseCategory.Category>) {
        val categoryDiffer = DifferAdapterEx(oldList, newList)
        val differ = DiffUtil.calculateDiff(categoryDiffer)
        oldList = newList
        differ.dispatchUpdatesTo(this)
    }
}