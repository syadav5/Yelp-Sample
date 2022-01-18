package com.rbc.yelp.ui.viewholders

import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rbc.yelp.R
import com.rbc.yelp.databinding.BusinessCardBinding
import com.rbc.yelp.services.models.Business
import com.rbc.yelp.utils.mapRatingToDrawable

class SearchResultsRecyclerViewHolder(private val binding: BusinessCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(business: Business) {
        binding.apply {
            business.imageUrl?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .centerCrop()
                    .placeholder(R.drawable.ic_add_a_photo)
                    .into(businessImg)
            } ?: kotlin.run {
                businessImg.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_add_a_photo
                    )
                )
            }
            val ratingImage = mapRatingToDrawable.get(business.rating ?: 0.0)
            Glide.with(itemView.context)
                .load(ratingImage)
                .centerCrop()
                .placeholder(R.drawable.stars_small_0)
                .into(ratingView)
            ratingView.contentDescription = getString(R.string.rating_content_desc,business.rating)
            businessName.text = business.name
            reviewCount.text = getReviewText(business.ratingCount)
        }
    }

    fun getString(@StringRes stringVal: Int, vararg formattingParams:Any):String {
        return itemView.context.resources.getString(stringVal, *formattingParams)
    }

    fun getReviewText(reviewCount: Int) =
        if (reviewCount > 0) {
            getString(R.string.review_content_desc, reviewCount)
        } else {
            getString(R.string.no_review)
        }
}