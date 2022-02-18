package com.example.stree20.ui.fragments.slacksetupfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stree20.data.remote.response.authresponse.AuthResponse
import com.example.stree20.data.repository.StreeRepo
import com.example.stree20.utils.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlackSetupViewModel @Inject constructor(
    private val repository: StreeRepo
) : ViewModel() {

    private val _auth = MutableStateFlow<State>(State.Idle)
    val auth = _auth.asStateFlow()

    fun getAuth(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String
    ) {

        viewModelScope.launch {
            repository.getAuth(clientId, clientSecret, code, redirectUri).collect { response ->
                _auth.value = State.Loading
                when (response) {
                    is Resource.Success -> {
                        _auth.value = State.Success(response.data)
                    }
                    is Resource.Error -> {
                        _auth.value = State.Error(response.message)
                    }
                }
            }
        }
    }


    sealed class State {
        data class Success(val auth: AuthResponse?) : State()
        data class Error(val msg: String?) : State()
        object Idle : State()
        object Loading : State()
    }

}