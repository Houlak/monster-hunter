package com.example.monster_hunter.views.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.monster_hunter.MyApp
import com.example.monster_hunter.R
import com.example.monster_hunter.data.models.Armor
import com.example.monster_hunter.databinding.ItemArmorBinding

class ArmorAdapter(
    private val context: Context,
    val onClick: (item: Armor) -> Unit,
    val onFavClick: (item: Armor, isChecked:Boolean, position:Int) -> Unit
) : ListAdapter<Armor, ArmorAdapter.ViewHolder>(DiffCallback()) {

    var favArmorsIds:List<Int> = listOf()

    private class DiffCallback : DiffUtil.ItemCallback<Armor>() {
        override fun areItemsTheSame(oldItem: Armor, newItem: Armor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Armor, newItem: Armor): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewBinding = ItemArmorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position],position)
    }

    override fun getItemCount(): Int = currentList.size


    fun addItems(list:List<Armor>,favIds:List<Int>) {
        submitList(list)
        favArmorsIds = favIds
    }
    fun addItems(list:List<Armor>) {
        submitList(null)
        submitList(list)
    }

    inner class ViewHolder(private val itemViewBinding: ItemArmorBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(armor: Armor, position: Int) {
            with(itemViewBinding) {
                lbName.text = armor.name
                lbDefense.text = (armor.defense?.base ?: 0).toString()
                lbRank.text = armor.rank

                ckFav.isChecked = favArmorsIds.contains(armor.id)

//                if (favArmorsIds.contains(armor.id)){
//                    ckFav.isChecked = true
//                }
//                ckFav.setOnCheckedChangeListener { compoundButton, b ->
//                    onFavClick.invoke(armor,b)
//                }
                ckFav.setOnClickListener {
                    onFavClick.invoke(armor,ckFav.isChecked,position)
                }
                rootLayout.setOnClickListener {
                    onClick.invoke(armor)
                }

                Glide.with(context)
                    .load(armor.assets?.imageMale)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imArmor)
            }
        }

    }



}