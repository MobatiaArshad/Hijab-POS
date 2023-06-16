package com.a71cities.hijab.ppm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.a71cities.hijab.ppm.databinding.ActivityMainBinding
import com.a71cities.hijab.ppm.extras.changeStatusBarColor
import com.a71cities.hijab.ppm.utils.bottomBar.BottomBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController
    lateinit var bottomBar: BottomBar
    private var passedBundle: Bundle ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        changeStatusBarColor(isLight = true)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        controller = navHostFragment.navController

        val bottomArray = arrayListOf(
            binding.bottomInc.homeIco,
            binding.bottomInc.productIco,
            binding.bottomInc.calendarIco,
            binding.bottomInc.profileIco,
        )

        bottomBar = BottomBar(bottomArray)
        bottomBar.setSelection(0)

        bottomArray.forEachIndexed { index, view ->
            view.setOnClickListener {
                bottomBar.setSelection(index)

                when(index) {
                    0 -> controller.navigate(R.id.homeFragment)
                    1 -> controller.navigate(R.id.productsFragment)
                    2 -> controller.navigate(R.id.salesReportFragment)
                    3 -> controller.navigate(R.id.profileFragment)
                }
            }
        }
    }

    private val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        binding.bottomInc.root.isVisible = arrayOf(
            R.id.homeFragment,
            R.id.productsFragment,
            R.id.salesReportFragment,
            R.id.profileFragment,
        ).any { it == controller.currentDestination?.id }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(binding.navHostFragment.id)
        return when (navController.currentDestination?.id) {
            R.id.homeFragment -> {
                finish()
                true
            }
            else -> navController.navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()
        controller.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        controller.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

    fun setBundle(bundle: Bundle?=null) {
        passedBundle = bundle
    }
}