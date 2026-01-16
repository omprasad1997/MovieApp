package com.example.mviapp.ui.movie.home

import com.example.mviapp.data.model.HomeSection

data class HomeState(
    val isLoading: Boolean = false,
    val sections: List<HomeSection> = emptyList(),
    val favouriteIds: Set<String> = emptySet(),
    val error: String? = null
)
