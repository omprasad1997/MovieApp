package com.example.mviapp

sealed class UserEffect {
    object NavigateToProfile : UserEffect()
    object NavigateBack : UserEffect()
}
