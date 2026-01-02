package com.example.mviapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun ProfileScreen(
    sharedViewModel: SharedViewModel,
    onBack: () -> Unit
) {

    val user by sharedViewModel
        .selectedUser
        .collectAsState()

    Column {
        Text(text = "Profile Screen")
        Text(text = "User ID: ${user?.id}")
        Text(text = "User Name: ${user?.name}")
        Button(onClick = onBack) {
            Text("Back to Home")
        }
    }
}

