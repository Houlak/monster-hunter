package com.example.monster_hunter.views.home

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.monster_hunter.R
import com.example.monster_hunter.data.models.Armor
import com.example.monster_hunter.databinding.DialogDetailArmorBinding


class ArmorDetailDialog(val armor: Armor) : DialogFragment() {

    private var _binding: DialogDetailArmorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDetailArmorBinding.inflate(inflater, container, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lbArmorName.text = armor.name
            lbRank.text = getString(R.string.armor_rank, armor.rank)
            lbDefenseMax.text = getString(R.string.defense_max, armor.defense?.max.toString())
            lbDefenseBase.text = getString(R.string.defense_base, armor.defense?.base.toString())
            lbDefenseAugmented.text =
                getString(R.string.defense_augmented, armor.defense?.augmented.toString())
            lbSlots.text = getString(R.string.armor_slots, armor.slots?.count().toString())
            lbIceDamage.text = armor.resistances?.ice.toString()
            lbDragonDamage.text = armor.resistances?.dragon.toString()
            lbThunderDamage.text = armor.resistances?.thunder.toString()
            lbFireDamage.text = armor.resistances?.fire.toString()
            lbWaterDamage.text = armor.resistances?.water.toString()
            lbType.text = getString(R.string.armor_type, armor.type?.replaceFirstChar {
                it.uppercase()
            })

            Glide.with(requireContext())
                .load(Armor.typeMap[armor.type?.lowercase()])
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imArmorType)

            Glide.with(requireContext())
                .load(armor.assets?.imageMale)
                .error(R.drawable.armor_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imArmorMale)

            Glide.with(requireContext())
                .load(armor.assets?.imageFemale)
                .error(R.drawable.armor_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imArmorFemale)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}