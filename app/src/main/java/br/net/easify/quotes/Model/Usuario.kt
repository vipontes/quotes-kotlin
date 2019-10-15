package br.net.easify.quotes.Model

import com.google.gson.annotations.SerializedName

data class Usuario (

    @SerializedName("usuarioId")
    val usuarioId: Int = 0,

    @SerializedName("usuarioNome")
    val usuarioNome: String = "",

    @SerializedName("usuarioEmail")
    val usuarioEmail: String = "",

    @SerializedName("usuarioSenha")
    val usuarioSenha: String = "",

    @SerializedName("usuarioAtivo")
    val usuarioAtivo: Int = 1,

    @SerializedName("usuarioSobre")
    val usuarioSobre: String = ""
)