package com.rbc.yelp.services.models

/**
 * Wrapper class to display business and category on UI.
 */
sealed class Item {
    data class HeaderItem(val name: String, val count: Int) : Item()
    data class DataItem(val uiData: BusinessCategoryUiModel) : Item()
}

/**
 * Model to display a business on UI
 */
data class BusinessCategoryUiModel(
    val categoryName: String,
    val business: Business
)