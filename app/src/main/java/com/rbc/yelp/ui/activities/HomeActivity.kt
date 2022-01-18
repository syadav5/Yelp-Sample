package com.rbc.yelp.ui.activities

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rbc.yelp.R
import com.rbc.yelp.databinding.FragmentContainerActivityBinding
import com.rbc.yelp.ui.fragmentFactory.HomeFragmentFactory
import com.rbc.yelp.ui.fragments.SearchFragment
import ui.NavigationDelegate
import ui.UiDelegate
import kotlin.reflect.KClass

class HomeActivity : AppCompatActivity(), NavigationDelegate, UiDelegate {
    private lateinit var binding: FragmentContainerActivityBinding
    val fragmentFactory by lazy {
        HomeFragmentFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        binding = FragmentContainerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progress.visibility = View.GONE
        addFragment(SearchFragment::class, false)
    }

    private fun <T: Fragment> addFragment(fragmentName: KClass<T>, addToBackStack: Boolean = true) {
        val fragment =
            supportFragmentManager.fragmentFactory.instantiate(classLoader, fragmentName.java.name)
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
                if(addToBackStack) {
                    transaction.addToBackStack(null)
                }
            transaction.commit()
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun setToolbarTitle(title: String, showBackButton: Boolean) {
        binding.toolbar.apply {
            this.title = title
            this.visibility = View.VISIBLE
            this.setNavigationOnClickListener {
                onBackPressed()
            }
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
            supportActionBar?.setDisplayShowHomeEnabled(showBackButton)
        }
    }

    override fun hideToolbar() {
        binding.toolbar.title = ""
        binding.toolbar.visibility = View.GONE
    }

    override fun hideKeyboard() {
        val view = currentFocus ?: View(this)
            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    onBackPressed()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun <T : Fragment> navigateTo(fragmentKClass: KClass<T>) {
        addFragment(fragmentKClass)
    }
}