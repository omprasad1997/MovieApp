package com.example.mviapp.ui.shared

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviapp.UserEffect
import com.example.mviapp.UserIntent
import com.example.mviapp.UserState
import com.example.mviapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    private val _effect = Channel<UserEffect>()
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: UserIntent) {
        when (intent) {

            is UserIntent.SelectUser -> {
                val user = User(intent.id, intent.name)

                _state.value = UserState(user)

                sendEffect(UserEffect.NavigateToProfile)
            }

            UserIntent.BackClicked -> {
                sendEffect(UserEffect.NavigateBack)
            }
        }
    }

    private fun sendEffect(effect: UserEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}