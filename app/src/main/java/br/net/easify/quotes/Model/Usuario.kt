package br.net.easify.quotes.Model

import com.google.gson.annotations.SerializedName

data class Usuario (

    @SerializedName("usuario_id")
    val usuarioId: Int = 0,

    @SerializedName("usuario_nome")
    val usuarioNome: String = "",

    @SerializedName("usuario_email")
    val usuarioEmail: String = "",

    @SerializedName("usuario_senha")
    val usuarioSenha: String = "",

    @SerializedName("usuario_ativo")
    val usuarioAtivo: Int = 1,

    @SerializedName("usuario_sobre")
    val usuarioSobre: String = ""
)