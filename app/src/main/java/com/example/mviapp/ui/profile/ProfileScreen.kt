package com.example.mviapp.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mviapp.mvi.UserIntent
import com.example.mviapp.mvi.UserState


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

