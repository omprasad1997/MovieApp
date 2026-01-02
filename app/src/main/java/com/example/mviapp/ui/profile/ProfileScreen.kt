package com.example.mviapp.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mviapp.UserIntent
import com.example.mviapp.UserState
import com.example.mviapp.ui.shared.SharedViewModel


@Composable
fun ProfileScreen(
    state: UserState,
    onIntent: (UserIntent) -> Unit
) {

    Column {
        Text(text = "Profile Screen")
        Text(text = "User ID: ${state.user?.id}")
        Text(text = "User Name: ${state.user?.name}")
        Button(onClick = { onIntent(UserIntent.BackClicked) }) {
            Text("Back to Home")
        }
    }
}

