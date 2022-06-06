package com.example.monster_hunter.data.models

import com.google.gson.annotations.SerializedName


data class ArmorSet(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("rank") var rank: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("pieces") var pieces: ArrayList<Int> = arrayListOf(),
    @SerializedName("bonus") var bonus: String? = null

)