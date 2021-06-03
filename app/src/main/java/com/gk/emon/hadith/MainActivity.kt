package com.gk.emon.hadith

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gk.emon.core_features.base_framework_ui.BaseActivity
import com.gk.emon.hadith.data.local.HadithPreference
import com.gk.emon.hadith.data.remote.apis.HadithApis
import com.gk.emon.lovelyLoading.LoadingPopup
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject
    lateinit var hadithPreference: HadithPreference
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hadithPreference.setString(HadithApis.api_key_label, HadithApis.api_key)
        setupNavigationDrawer()
        setSupportActionBar(findViewById(R.id.toolbar))
        setupNavigation()
        setupLoading()
    }

    private fun setupNavigation() {
        val navController: NavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration =
            AppBarConfiguration.Builder(R.id.collection_fragment_dest)
                .setDrawerLayout(drawerLayout)
                .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)
    }

    //A singleton global configuration for loading UI which will be helpful to reduce boilerplate
    //loading pop related code
    private fun setupLoading() {
        LoadingPopup.getInstance(this)
            .defaultLovelyLoading()
            .build()
    }

    override fun getViewContainer(): View {
        return findViewById(R.id.drawer_layout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
    }
}