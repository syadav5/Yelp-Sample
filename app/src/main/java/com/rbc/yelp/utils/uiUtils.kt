package com.rbc.yelp.utils

import android.content.Context
import android.widget.Toast
import com.rbc.yelp.R

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

val mapRatingToDrawable: Map<Double, Int> = mutableMapOf(
    0.0 to R.drawable.stars_small_0,
    1.0 to R.drawable.stars_small_1,
    1.5 to R.drawable.stars_small_1_half,
    2.0 to R.drawable.stars_small_2,
    2.5 to R.drawable.stars_small_2_half,
    3.0 to R.drawable.stars_small_3,
    3.5 to R.drawable.stars_small_3_half,
    4.0 to R.drawable.stars_small_4,
    4.5 to R.drawable.stars_small_4_half,
    5.0 to R.drawable.stars_small_5
)