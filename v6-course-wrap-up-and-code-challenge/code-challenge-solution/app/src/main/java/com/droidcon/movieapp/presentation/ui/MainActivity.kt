package com.droidcon.movieapp.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.droidcon.movieapp.common.ConnectivityObserver
import com.droidcon.movieapp.common.NetworkConnectivityObserver
import com.droidcon.movieapp.presentation.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            val status by connectivityObserver.observe().collectAsState(
                initial = null,
            )

            if (status?.ordinal == 0 || status?.ordinal == 1) {
                Toast.makeText(this, "Network Internet: ${status?.name}", Toast.LENGTH_LONG).show()
            }

            MoviesApp()
        }
    }

    @Composable
    fun MoviesApp() {
        MovieAppTheme {
            val navHostController = rememberNavController()

            MoviesAppNavHost(
                navHostController = navHostController,
            )
        }
    }
}
