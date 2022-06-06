package com.example.monster_hunter.data.repositories

import android.content.SharedPreferences
import com.example.monster_hunter.utils.customFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FavArmorRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    FavArmorRepository {

    private val thread = Dispatchers.IO

    override suspend fun fetchFavArmors(): Flow<List<Int>> {
        return customFlow<List<Int>> {
            emit(sharedPreferences.all.values.toList() as List<Int>)
        }.flowOn(thread)
    }

    override suspend fun storeFavArmor(id: Int): Flow<List<Int>> {
        return customFlow<List<Int>> {
            sharedPreferences.edit().putInt("$id", id).apply()
            emit(sharedPreferences.all.values.toList() as List<Int>)
        }.flowOn(thread)
    }

    override suspend fun deleteFavArmor(id: Int): Flow<List<Int>> {
        return customFlow<List<Int>> {
            sharedPreferences.edit().remove(id.toString()).apply()
            emit(sharedPreferences.all.values.toList() as List<Int>)
        }.flowOn(thread)
    }


}

interface FavArmorRepository {
    suspend fun fetchFavArmors(): Flow<List<Int>>
    suspend fun storeFavArmor(id: Int): Flow<List<Int>>
    suspend fun deleteFavArmor(id: Int): Flow<List<Int>>

}