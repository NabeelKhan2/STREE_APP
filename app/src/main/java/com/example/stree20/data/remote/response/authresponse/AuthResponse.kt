package com.example.stree20.data.remote.response.authresponse

data class AuthResponse(
    val access_token: String,
    val enterprise_id: Any,
    val scope: String,
    val team_id: String,
    val team_name: String
)