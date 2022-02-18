package com.example.stree20.data.remote.response.msgresponse

data class MessageResponse(
    val channel: String,
    val message: Message,
    val ok: Boolean,
    val ts: String
)