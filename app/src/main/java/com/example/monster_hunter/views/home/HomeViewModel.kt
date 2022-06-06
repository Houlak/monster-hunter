package com.example.monster_hunter.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monster_hunter.data.models.Armor
import com.example.monster_hunter.data.repositories.ArmorRepository
import com.example.monster_hunter.data.repositories.FavArmorRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val armorRepository: ArmorRepository,
    private val favArmorRepository: FavArmorRepository
) : ViewModel() {

    val getArmorsResponse = MutableStateFlow<GetArmorsResponse>(GetArmorsLoading)
    val updateFavArmorResponse = MutableStateFlow<UpdateFavArmorsResponse>(UpdateFavArmorsIdle)
    val searchArmorsResponse = MutableStateFlow<SearchArmorsResponse>(SearchArmorsIdle)
    val favoriteArmorsResponse = MutableStateFlow<FavArmorsResponse>(FavArmorsIdle)

    private var unfilteredArmors: List<Armor> = listOf()
    var armors: List<Armor> = listOf()
    var favArmorsId: List<Int> = listOf()
    var isFavChecked:Boolean = false

    fun searchArmorByName(armorName:String) {
        val result = armors.filter { searchArmor ->
            searchArmor.name?.lowercase()!!.startsWith(armorName.lowercase())
        }
        when {
            armorName.isEmpty() -> {
                if (!isFavChecked){
                    armors = unfilteredArmors
                }
                searchArmorsResponse.value = SearchArmorsSuccess(armors)
            }
            result.isNotEmpty() ->{
                armors = result
                searchArmorsResponse.value = SearchArmorsSuccess(result)
            }
            else -> {
                searchArmorsResponse.value = SearchArmorsEmpty(armors)
            }
        }
    }

    fun filterFavArmors(isChecked:Boolean) {
        isFavChecked = isChecked
        if (isChecked) {
            val result = armors.filter { searchArmor ->
               favArmorsId.contains( searchArmor.id)
            }
            when {
                result.isNotEmpty() -> {
                    armors = result
                    favoriteArmorsResponse.value = FavArmorsSuccess(result)
                }
                else -> {
                    armors = unfilteredArmors
                    favoriteArmorsResponse.value = FavArmorsEmpty(armors)
                }
            }
        } else {
            armors = unfilteredArmors
            favoriteArmorsResponse.value = FavArmorsUnchecked(armors)
        }
    }

    fun saveFavArmor(armorId: Int,position: Int) {
        viewModelScope.launch {
            favArmorRepository.storeFavArmor(armorId)
                .catch {
                    updateFavArmorResponse.value = UpdateFavSaveError(armorId,position)
                }
                .collect { newIdsList ->
                    favArmorsId = newIdsList
                    updateFavArmorResponse.value = UpdateFavArmorsSuccess(newIdsList,position)
                }
        }
    }

    fun deleteFavArmor(armorId: Int,position: Int) {
        viewModelScope.launch {
            favArmorRepository.deleteFavArmor(armorId)
                .catch {
                    updateFavArmorResponse.value = UpdateFavDeleteError(armorId,position)
                }
                .collect {newIdsList ->
                    favArmorsId = newIdsList
                    updateFavArmorResponse.value = UpdateFavArmorsSuccess(newIdsList,position)
                }
        }
    }

    fun getFavArmors() {
        viewModelScope.launch {
            favArmorRepository.fetchFavArmors()
                .catch {
                    it.message
                }
                .collect {
                    favArmorsId = it
                }
        }
    }

    fun getArmors() {
        viewModelScope.launch {
            armorRepository.fetchArmors()
                .onStart {
                    getArmorsResponse.value = GetArmorsLoading
                }
                .combine(favArmorRepository.fetchFavArmors()) { response1, response2 ->
                    armors = response1
                    unfilteredArmors = response1
                    favArmorsId = response2
                }
                .catch {
                    //this delay its just to show that the states are changing properly
                    delay(500)
                    getArmorsResponse.value = GetArmorsError
                }
                .collect {
                    getArmorsResponse.value = GetArmorsSuccess(armors)
                }
        }
    }


}

sealed class GetArmorsResponse
data class GetArmorsSuccess(val data: List<Armor>) : GetArmorsResponse()
object GetArmorsError : GetArmorsResponse()
object GetArmorsLoading : GetArmorsResponse()

sealed class UpdateFavArmorsResponse
data class UpdateFavArmorsSuccess(val ids:List<Int>, val position:Int) : UpdateFavArmorsResponse()
object UpdateFavArmorsIdle : UpdateFavArmorsResponse()
data class UpdateFavSaveError(val armorId: Int, val position:Int) : UpdateFavArmorsResponse()
data class UpdateFavDeleteError(val armorId: Int, val position:Int) : UpdateFavArmorsResponse()

sealed class SearchArmorsResponse
data class SearchArmorsSuccess(val data: List<Armor>) :SearchArmorsResponse()
object SearchArmorsIdle : SearchArmorsResponse()
data class SearchArmorsEmpty(val data: List<Armor>) : SearchArmorsResponse()

sealed class FavArmorsResponse
data class FavArmorsSuccess(val data: List<Armor>) :FavArmorsResponse()
object FavArmorsIdle : FavArmorsResponse()
data class FavArmorsEmpty(val data: List<Armor>): FavArmorsResponse()
data class FavArmorsUnchecked(val data: List<Armor>): FavArmorsResponse()




