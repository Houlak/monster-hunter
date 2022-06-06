package com.example.monster_hunter.data.models

import com.google.gson.annotations.SerializedName


data class Assets(

    @SerializedName("imageMale") var imageMale: String? = null,
    @SerializedName("imageFemale") var imageFemale: String? = null

)