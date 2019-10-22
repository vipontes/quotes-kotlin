package br.net.easify.quotes.Model

import com.google.gson.annotations.SerializedName

data class Usuario (

    @SerializedName("usuarioId")
    var usuarioId: Int = 0,

    @SerializedName("usuarioNome")
    var usuarioNome: String = "",

    @SerializedName("usuarioEmail")
    var usuarioEmail: String = "",

    @SerializedName("usuarioSenha")
    var usuarioSenha: String? = "",

    @SerializedName("usuarioAtivo")
    var usuarioAtivo: Int? = 1,

    @SerializedName("usuarioSobre")
    var usuarioSobre: String? = ""
)