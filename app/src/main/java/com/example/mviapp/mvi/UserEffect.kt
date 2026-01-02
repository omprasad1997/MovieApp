package com.example.mviapp.mvi

sealed class UserEffect {
    object NavigateToProfile : UserEffect()
    object NavigateBack : UserEffect()
}
