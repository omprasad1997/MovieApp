package com.example.mviapp.mvi

sealed class UserIntent {
    data class SelectUser(val id: Int, val name: String) : UserIntent()
    object BackClicked : UserIntent()
    data class SearchMovie(val query: String) : UserIntent()
}
