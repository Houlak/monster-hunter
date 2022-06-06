package com.example.monster_hunter.data.models

import com.example.monster_hunter.R
import com.google.gson.annotations.SerializedName


data class Armor(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("rank") var rank: String? = null,
    @SerializedName("rarity") var rarity: Int? = null,
    @SerializedName("defense") var defense: Defense? = null,
    @SerializedName("resistances") var resistances: Resistances? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("slots") var slots: ArrayList<Slots>? = null,
    @SerializedName("skills") var skills: ArrayList<Skills>? = null,
    @SerializedName("armorSet") var armorSet: ArmorSet? = null,
    @SerializedName("assets") var assets: Assets? = null,
    @SerializedName("crafting") var crafting: Crafting? = null,

) {
    companion object {
        var typeMap = mapOf<String,Int>(
            "head" to R.drawable.ic_head,
            "chest" to R.drawable.ic_chest,
            "gloves" to R.drawable.ic_gloves,
            "waist" to R.drawable.ic_waist,
            "legs" to R.drawable.ic_legs
        )
    }

}