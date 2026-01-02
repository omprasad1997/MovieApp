package com.example.mviapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mviapp.ui.theme.MVIAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVIAppTheme {
                AppNavGraph()
            }
        }
    }
}

//Step 3 Setup Navigation
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) { entry ->
                // Hilt ViewModel scoped to Home
                val sharedViewModel: SharedViewModel =
                    hiltViewModel(entry)

                HomeScreen(
                    onNavigateToProfile = { id, name ->
                        // 1️⃣ Set data in ViewModel
                        sharedViewModel.setUser(
                            User(id = id, name = name)
                        )

                        navController.navigate(Screen.Profile.route)
                    }
                )
            }

            composable(Screen.Profile.route) { backStackEntry -> //backStackEntry.arguments → like Intent extras

                //SAME ViewModelStoreOwner → shared VM
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Screen.Home.route)
                }

                val sharedViewModel: SharedViewModel = hiltViewModel(parentEntry)

                ProfileScreen(
                    sharedViewModel = sharedViewModel,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}


