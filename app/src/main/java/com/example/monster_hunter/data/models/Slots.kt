package com.example.monster_hunter.data.models

import com.google.gson.annotations.SerializedName


data class Slots(

    @SerializedName("type") var type: String? = null,
    @SerializedName("subtype") var subtype: String? = null,
    @SerializedName("rank") var rank: String? = null,
    @SerializedName("quantity") var quantity: Int? = null,
    @SerializedName("chance") var chance: Int? = null
)