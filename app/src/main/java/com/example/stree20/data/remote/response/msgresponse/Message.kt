package com.example.stree20.data.remote.response.msgresponse

data class Message(
    val attachments: List<Attachment>,
    val bot_id: String,
    val subtype: String,
    val text: String,
    val ts: String,
    val type: String,
    val username: String
)