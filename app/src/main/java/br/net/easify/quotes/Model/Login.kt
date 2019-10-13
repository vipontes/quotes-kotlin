package br.net.easify.quotes.Model

import com.google.gson.annotations.SerializedName

data class Login (

    @SerializedName("token")
    val token: String,

    @SerializedName("refresh_token")
    val refreshToken: String
)
