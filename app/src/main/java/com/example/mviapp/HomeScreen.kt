package com.example.mviapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Step 2 Create Screens
@Composable
fun HomeScreen(
    onNavigateToProfile: (Int, String) -> Unit
) {
    Column {
        Text("Home Screen")
        Button(onClick = { onNavigateToProfile(101,"Omi") }) {
            Text("Go to Profile")
        }
    }
}