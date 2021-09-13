package com.tawk.to.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tawk.to.R
import dagger.hilt.android.AndroidEntryPoint
import android.net.Network
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.tawk.to.databinding.ActivityMainBinding
import com.tawk.to.ktx.goneIfOrVisible
import com.tawk.to.ktx.visibleIfOrGone
import com.tawk.to.network.NetworkUtils
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var networkUtils: NetworkUtils

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(navController)

        // show or hide no internet ui
        viewModel.getNetworkStatus().observe(this) {
            binding.noInternetGroup.visibleIfOrGone(it == Disconnected)
        }

        // hide toolbar for search fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.appBarLayout.goneIfOrVisible(destination.id == R.id.searchFragment)
        }

        // region network connectivity status
        viewModel.updateNetworkStatus(if (networkUtils.isConnected()) Connected else Disconnected)

        val networkRequest = NetworkRequest.Builder().apply {
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        }.build()


        //handle internet connectivity
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                viewModel.updateNetworkStatus(Connected)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                viewModel.updateNetworkStatus(Disconnected)
            }
        })
        // endregion


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}