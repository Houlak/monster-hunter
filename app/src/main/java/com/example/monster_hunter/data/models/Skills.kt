package com.example.monster_hunter.data.models

import com.example.example.Modifiers
import com.google.gson.annotations.SerializedName


data class Skills(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("level") var level: Int? = null,
    @SerializedName("modifiers") var modifiers: Modifiers? = Modifiers(),
    @SerializedName("description") var description: String? = null,
    @SerializedName("skill") var skill: Int? = null,
    @SerializedName("skillName") var skillName: String? = null

)