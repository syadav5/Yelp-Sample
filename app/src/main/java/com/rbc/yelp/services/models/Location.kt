package com.rbc.yelp.services.models

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("display_address")
    val displayAddress: List<String>
)