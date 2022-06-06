package com.example.monster_hunter.data.repositories

import android.content.SharedPreferences
import com.example.monster_hunter.data.models.Armor
import com.example.monster_hunter.retrofit.WebServices
import com.example.monster_hunter.utils.customFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.KoinComponent
import org.koin.core.inject

class ArmorRepositoryImpl(private val api: WebServices) : ArmorRepository {

    private val thread = Dispatchers.IO

    override suspend fun fetchArmors(): Flow<List<Armor>> {

        return customFlow<List<Armor>> {
            emit(api.getAllArmors())
        }
            .flowOn(thread)
    }
}

interface ArmorRepository {
    suspend fun fetchArmors(): Flow<List<Armor>>
}