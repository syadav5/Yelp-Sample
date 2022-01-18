package com.rbc.yelp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rbc.yelp.R
import com.rbc.yelp.databinding.BusinessDetailsFragmentBinding
import com.rbc.yelp.utils.mapRatingToDrawable
import com.rbc.yelp.viewmodels.HomeSharedViewModel

class BusinessDetailsFragment : BaseFragment() {

    private var _binding: BusinessDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: HomeSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BusinessDetailsFragmentBinding.inflate(inflater)
        initUi()
        return binding.root
    }

    private fun initUi() {
        uiDelegate.hideToolbar()
        sharedViewModel.selectedBusiness?.let { business ->
            binding.toolbarId.title = business.name
            addImage(business.imageUrl, null, R.drawable.ic_add_a_photo, binding.businessImage)
            binding.scrollingContent.apply {
                if (business.isClosed) {
                    this.openOrCloseTv.text = getString(R.string.closed)
                    this.openOrCloseTv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                } else {
                    this.openOrCloseTv.text = getString(R.string.open)
                    this.openOrCloseTv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green
                        )
                    )
                }
                addImage(
                    imageUrl = null, drawable = mapRatingToDrawable.get(business.rating ?: 0.0),
                    defaultImg = mapRatingToDrawable.get(0.0)!!,
                    imageView = this.ratingsImgView
                )
                this.categoriesTv.text =
                    business.categories.map { it.title }.joinToString()
                if (!TextUtils.isEmpty(business.isDisplayPhone)) {
                    this.phoneTv.text = getString(R.string.call, business.isDisplayPhone)
                } else {
                    this.phoneTv.visibility = View.GONE
                }
                this.addressTv.text = business.location.displayAddress.joinToString("\n")
            }
        }
    }

    private fun addImage(
        imageUrl: String? = null,
        drawable: Int? = null,
        defaultImg: Int,
        imageView: ImageView
    ) {
        Glide.with(requireContext())
            .load(imageUrl ?: drawable)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(defaultImg)
            .into(imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}