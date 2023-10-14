package com.info.clickshop.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.info.clickshop.R
import com.info.clickshop.common.util.gone
import com.info.clickshop.common.util.visible
import com.info.clickshop.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val popupMenu= PopupMenu(this,null)
        popupMenu.inflate(R.menu.nav_bar)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        navController.addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when (destination.id) {
                    R.id.splashFragment,
                    R.id.loginFragment,
                    R.id.registerFragment,
                    R.id.seeMoreFlashFragment,
                    R.id.seeMoreMegaFragment,
                    R.id.detailFragment,
                    R.id.listCategoryFragment,
                    R.id.categoryProductsFragment,
                    R.id.favoriteFragment,
                    R.id.profileFragment,
                    -> binding.bottomBar.gone()

                    else -> {
                        if (binding.bottomBar.visibility==View.GONE) {
                            binding.bottomBar.visible()
                        }
                    }
                }
            }
        })
    }
}