package com.example.mviapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mviapp.UserIntent

//Step 2 Create Screens
@Composable
fun HomeScreen(
    onIntent: (UserIntent) -> Unit
) {
    Column {
        Text("Home Screen")
        Button(onClick = { onIntent(UserIntent.SelectUser(101, "Omi")) }) {
            Text("Go to Profile")
        }
    }
}