package com.rbc.yelp.ui.fragmentFactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rbc.yelp.ui.fragments.BusinessDetailsFragment
import com.rbc.yelp.ui.fragments.ResultsFragment
import com.rbc.yelp.ui.fragments.SearchFragment

/**
 * Fragmen Factory.
 */
class HomeFragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SearchFragment::class.java.name -> SearchFragment()
            ResultsFragment::class.java.name -> ResultsFragment()
            BusinessDetailsFragment::class.java.name -> BusinessDetailsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}