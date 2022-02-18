package com.example.stree20.ui.fragments.grouplistfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stree20.data.local.StreeItem
import com.example.stree20.data.repository.StreeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupFragmentViewModel @Inject constructor(
    private val repository: StreeRepo
) : ViewModel() {

    private val _streeGroup = MutableStateFlow<State>(State.Idle)
    val readStreeGroup = _streeGroup.asStateFlow()

    init {
        observerStreeItem()
    }

    private fun observerStreeItem() {
        viewModelScope.launch {
            repository.observeAllStreeItem().collect {
                _streeGroup.value = State.Success(it)
            }
        }
    }

    fun deleteStreeItem(streeItem: StreeItem) = viewModelScope.launch {
        repository.deleteStreeItem(streeItem)
    }

    fun insertStreeItem(streeItem: StreeItem) = viewModelScope.launch {
        repository.insertStreeItem(streeItem)
    }


    sealed class State {
        data class Success(val streeGroup: List<StreeItem>) : State()
        object Idle : State()
    }

}