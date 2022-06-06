package com.example.monster_hunter.data.models

import com.google.gson.annotations.SerializedName


data class Resistances(

    @SerializedName("fire") var fire: Int? = null,
    @SerializedName("water") var water: Int? = null,
    @SerializedName("ice") var ice: Int? = null,
    @SerializedName("thunder") var thunder: Int? = null,
    @SerializedName("dragon") var dragon: Int? = null

)