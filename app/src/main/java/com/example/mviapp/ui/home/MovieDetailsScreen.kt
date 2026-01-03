package com.example.mviapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mviapp.data.model.Movie

@Composable
fun MovieDetailsScreen(
    movie: Movie?,
    onBack: () -> Unit
) {
    if (movie == null) {
        Text("No movie selected")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Year: ${movie.year}")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
