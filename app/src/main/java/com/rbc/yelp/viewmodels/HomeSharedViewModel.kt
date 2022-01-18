package com.rbc.yelp.viewmodels

import androidx.lifecycle.ViewModel
import com.rbc.yelp.services.models.Business
import com.rbc.yelp.services.models.BusinessCategoryUiModel
import com.rbc.yelp.services.models.Item
import com.rbc.yelp.services.models.SearchResult
import java.util.*

class HomeSharedViewModel : ViewModel() {

    var searchResult: SearchResult? = null
    var selectedLocation: String? = null
    var searchTerm: String? = ""
    var selectedBusiness: Business? = null
    private var sortAscOrder = true
    var groupedBusinessMap : Map<String, List<Item.DataItem>>? = mapOf()
    var dataList:MutableList<Item> = mutableListOf()

    fun toggleSortOrder(asc: Boolean){
        sortAscOrder = asc
    }

    fun loadData(){
        getListOfBusinessesGroupedByCategory()
    }

    private fun getListOfBusinessesGroupedByCategory() {
        val flattenedBusinessData = flattenBusinessData()
        groupSortedData(flattenedBusinessData)
        convertMapToItemsList()
    }

    private fun convertMapToItemsList() {
        dataList.clear()
        dataList.addAll(groupedBusinessMap?.flatMap { currentMapEntry ->
            val currentList = currentMapEntry.value
            listOf(Item.HeaderItem(currentMapEntry.key, currentList.size)) +
                    currentList
        }?.toMutableList() ?: mutableListOf())
    }

    private fun flattenBusinessData(): MutableList<Item.DataItem> {
        val flattenedBusinessData: MutableList<Item.DataItem> = mutableListOf()
        searchResult?.businesses?.map { eachBusiness ->
            // Iterate over each category.
            eachBusiness.categories.forEach { eachCategory ->
                flattenedBusinessData.add(
                    Item.DataItem(
                        uiData =
                        BusinessCategoryUiModel(eachCategory.title, eachBusiness)
                    )
                )
            }
        }
        return flattenedBusinessData
    }

    fun groupSortedData(flattenedBusinessData: MutableList<Item.DataItem>) = if(sortAscOrder) {
        groupedBusinessMap = flattenedBusinessData
            .groupBy { it.uiData.categoryName }.toSortedMap()
    } else {
        groupedBusinessMap = flattenedBusinessData
            .groupBy { it.uiData.categoryName }.toSortedMap(Collections.reverseOrder())
    }

    fun clear() {
        selectedBusiness = null
        searchTerm = ""
        selectedLocation = null
        searchResult = null
    }
}