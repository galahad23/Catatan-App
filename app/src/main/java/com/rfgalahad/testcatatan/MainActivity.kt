package com.rfgalahad.testcatatan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rfgalahad.testcatatan.data.local.Catatan
import com.rfgalahad.testcatatan.ui.screen.BuatCatatanScreen
import com.rfgalahad.testcatatan.ui.screen.ListCatatanScreen
import com.rfgalahad.testcatatan.ui.screen.UbahCatatanScreen
import com.rfgalahad.testcatatan.viewmodel.CatatanViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: CatatanViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory(application))
            val navController = rememberNavController()
            val catatanList by viewModel.allCatatan.observeAsState(emptyList())

            NavHost(navController, startDestination = Screen.ListCatatan.route) {

                composable(Screen.ListCatatan.route) {
                    ListCatatanScreen(
                        catatanList = catatanList,
                        onAddClick = { navController.navigate(Screen.BuatCatatan.route) },
                        onUpdateClick = { catatan -> navController.navigate(Screen.UbahCatatan.createRoute(catatan.id)) },
                        onDeleteClick = { catatan -> viewModel.delete(catatan) }
                    )
                }

                composable(Screen.BuatCatatan.route) {
                    BuatCatatanScreen(
                        onSave = {
                            viewModel.insert(it)
                            navController.popBackStack()
                        },
                        onCancel = { navController.popBackStack() }
                    )
                }

                composable(
                    route = Screen.UbahCatatan.route,
                    arguments = listOf(navArgument("catatanId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("catatanId") ?: return@composable
                    var catatan by remember { mutableStateOf<Catatan?>(null) }

                    LaunchedEffect(id) {
                        catatan = viewModel.getCatatanById(id)
                    }

                    catatan?.let {
                        UbahCatatanScreen(
                            original = it,
                            onUpdate = { updated ->
                                viewModel.update(updated)
                                navController.popBackStack()
                            },
                            onCancel = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}