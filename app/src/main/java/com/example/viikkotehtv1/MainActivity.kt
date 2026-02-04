package com.example.viikkotehtv1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikkotehtv1.ViewModel.TaskViewModel
import com.example.viikkotehtv1.ui.theme.Viikkotehtävä1Theme
import com.example.viikkotehtv1.view.CalendarScreen
import com.example.viikkotehtv1.view.HomeScreen

const val ROUTE_HOME = "home"
const val ROUTE_CALENDAR = "calendar"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Viikkotehtävä1Theme {

                val navController = rememberNavController()
                val viewModel: TaskViewModel = viewModel()

                Scaffold { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(ROUTE_HOME) {
                            HomeScreen(
                                viewModel = viewModel,
                                onGoCalendar = {
                                    navController.navigate(ROUTE_CALENDAR)
                                }
                            )
                        }

                        composable(ROUTE_CALENDAR) {
                            CalendarScreen(
                                viewModel = viewModel,
                                onGoHome = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        viewModel = TaskViewModel(),
        onGoCalendar = {}
    )
}