package com.example.monster_hunter.data.models

import com.example.example.Item
import com.google.gson.annotations.SerializedName


data class Materials (

  @SerializedName("quantity" ) var quantity : Int?  = null,
  @SerializedName("item"     ) var item     : Item? = Item()

)