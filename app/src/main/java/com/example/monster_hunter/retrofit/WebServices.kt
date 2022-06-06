package com.example.monster_hunter.retrofit

import com.example.monster_hunter.data.models.Armor
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface WebServices {

    @GET("armor")
    suspend fun getAllArmors(
    ): List<Armor>
}