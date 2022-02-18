package com.example.stree20.data.remote.response.channelresponse

data class ChannelResponse(
    val channels: List<Channel>,
    val ok: Boolean,
    val response_metadata: ResponseMetadata
)