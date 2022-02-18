package com.example.stree20.ui.fragments.addandupdategroupfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stree20.data.local.StreeItem
import com.example.stree20.data.remote.response.channelresponse.ChannelResponse
import com.example.stree20.data.repository.StreeRepo
import com.example.stree20.utils.constants.Constants
import com.example.stree20.utils.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddAndUpdateViewModel @Inject constructor(
    private val repository: StreeRepo
) : ViewModel() {

    private val _channel = MutableStateFlow<State>(State.Idle)
    val channel = _channel.asStateFlow()

    private val eventChannel = Channel<Event>()
    val eventFLow = eventChannel.receiveAsFlow()


    fun insertShoppingItemIntoDb(streeItem: StreeItem) = viewModelScope.launch {
        repository.insertStreeItem(streeItem)
    }

    fun updateStreeItem(streeItem: StreeItem) = viewModelScope.launch {
        repository.updateStreeItem(streeItem)
    }

    fun getChannel(token: String) {
        viewModelScope.launch {
            repository.getChannel(token).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _channel.value = State.Success(response.data)
                    }
                    is Resource.Error -> {
                        _channel.value = State.Error(response.message)
                    }
                }
            }
        }
    }

    fun insertStreeItem(
        groupName: String,
        smsSource: String,
        channel: String
    ) {
        viewModelScope.launch {
            if (groupName.isEmpty() || smsSource.isEmpty() || channel.isEmpty()) {
                eventChannel.send(Event.Error("The fields must not be empty"))
                return@launch
            }
            if (groupName.length > Constants.MAX_NAME_LENGTH) {
                eventChannel.send(
                    Event.Error(
                        "The name of the group" +
                                "must not exceed ${Constants.MAX_NAME_LENGTH} characters"
                    )
                )
                return@launch
            }

            val source = try {
                smsSource.toInt()
            } catch (e: Exception) {
                eventChannel.send(Event.Error("Please enter a valid number"))
                return@launch
            }

            val streeItem =
                StreeItem(groupName, source, channel)

            insertShoppingItemIntoDb(streeItem)
            eventChannel.send(Event.Success(streeItem))
        }
    }

    sealed class State {
        data class Success(val channel: ChannelResponse?) : State()
        data class Error(val msg: String?) : State()
        object Idle : State()
    }

    sealed class Event {
        data class Success(val channelEvent: StreeItem) : Event()
        data class Error(val msg: String?) : Event()
    }

}