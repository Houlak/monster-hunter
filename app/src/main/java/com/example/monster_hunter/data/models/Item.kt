package com.example.example

import com.google.gson.annotations.SerializedName


data class Item (

  @SerializedName("id"          ) var id          : Int?    = null,
  @SerializedName("rarity"      ) var rarity      : Int?    = null,
  @SerializedName("carryLimit"  ) var carryLimit  : Int?    = null,
  @SerializedName("value"       ) var value       : Int?    = null,
  @SerializedName("name"        ) var name        : String? = null,
  @SerializedName("description" ) var description : String? = null

)