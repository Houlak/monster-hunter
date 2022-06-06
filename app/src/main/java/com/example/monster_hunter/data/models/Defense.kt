package com.example.monster_hunter.data.models

import com.google.gson.annotations.SerializedName


data class Defense(

    @SerializedName("base") var base: Int? = null,
    @SerializedName("max") var max: Int? = null,
    @SerializedName("augmented") var augmented: Int? = null

)