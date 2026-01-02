package com.example.mviapp

sealed class UserIntent {
    data class SelectUser(val id: Int, val name: String) : UserIntent()
    object BackClicked : UserIntent()
}
