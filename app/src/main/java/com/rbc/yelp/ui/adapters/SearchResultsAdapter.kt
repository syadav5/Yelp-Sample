package com.rbc.yelp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbc.yelp.databinding.BusinessCardBinding
import com.rbc.yelp.databinding.HeaderViewBinding
import com.rbc.yelp.services.models.Item
import com.rbc.yelp.ui.fragments.businessCardItemClickListener
import com.rbc.yelp.ui.viewholders.HeaderViewHolder
import com.rbc.yelp.ui.viewholders.SearchResultsRecyclerViewHolder

class SearchResultsAdapter(val searchResults: List<Item>, val businessCardItemClickListener: businessCardItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            DataType.HEADER.ordinal ->
                HeaderViewHolder(
                    HeaderViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ).root
                )
            else -> SearchResultsRecyclerViewHolder(
                BusinessCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = searchResults[position]
        if (holder is SearchResultsRecyclerViewHolder) {
            (currentItem as? Item.DataItem)?.let { item ->
                holder.bind(item.uiData.business)
                holder.itemView.setOnClickListener {
                    businessCardItemClickListener.invoke(item.uiData.business)
                }
            }

        } else if (holder is HeaderViewHolder) {
            (currentItem as? Item.HeaderItem)?.let {
                holder.header.text = it.name + "(" + it.count + ")"
            }
        }
    }

    override fun getItemCount(): Int {
       return searchResults.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = searchResults[position]
        return when (item) {
            is Item.HeaderItem -> DataType.HEADER.ordinal
            is Item.DataItem -> DataType.DATA.ordinal
        }
    }

    enum class DataType{HEADER, DATA}
}