package com.example.monster_hunter.data.repositories

import android.content.SharedPreferences
import com.example.monster_hunter.utils.customFlow
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class FavArmorRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    FavArmorRepository {

    private val thread = Dispatchers.IO

    override suspend fun fetchFavArmors(): Flow<List<Int>> {
        return customFlow<List<Int>> {
            val listStr = sharedPreferences.getString(FavArmorRepository.STORAGE_TAG,  "[]")
            val list: IntArray = Gson().fromJson(listStr, IntArray::class.java)
            emit(list.toList())
        }.flowOn(thread)
    }

    override suspend fun storeFavArmor(id: Int): Flow<List<Int>> {
        return customFlow<List<Int>> {
            val listStr = sharedPreferences.getString(FavArmorRepository.STORAGE_TAG, "[]")
            val list: MutableList<Int> = Gson().fromJson(listStr, IntArray::class.java).toMutableList()
            list.add(id)
            val listToStore = Gson().toJson(list)
            sharedPreferences.edit().putString(FavArmorRepository.STORAGE_TAG, listToStore).apply()
            emit(list.toList())
        }.flowOn(thread)
    }

    override suspend fun deleteFavArmor(id: Int): Flow<List<Int>> {
        return customFlow<List<Int>> {
            val listStr = sharedPreferences.getString(FavArmorRepository.STORAGE_TAG, "[]")
            val list: MutableList<Int> = Gson().fromJson(listStr, IntArray::class.java).toMutableList()
            list.remove(id)
            val listToStore = Gson().toJson(list)
            sharedPreferences.edit().putString(FavArmorRepository.STORAGE_TAG, listToStore).apply()
            emit(list.toList())
        }.flowOn(thread)
    }


}

interface FavArmorRepository {
    suspend fun fetchFavArmors(): Flow<List<Int>>
    suspend fun storeFavArmor(id: Int): Flow<List<Int>>
    suspend fun deleteFavArmor(id: Int): Flow<List<Int>>

    companion object {
        const val STORAGE_TAG = "STORAGE_TAG"
    }
}