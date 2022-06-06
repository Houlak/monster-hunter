package com.example.monster_hunter.data.models

import com.google.gson.annotations.SerializedName


data class Crafting(

    @SerializedName("materials") var materials: ArrayList<Materials> = arrayListOf()

)