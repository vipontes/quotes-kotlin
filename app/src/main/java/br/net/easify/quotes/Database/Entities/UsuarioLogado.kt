package br.net.easify.quotes.Database.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class UsuarioLogado (

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "usuario_id")
    var usuarioId: Int = 0,

    @NonNull
    @ColumnInfo(name = "usuario_nome")
    var usuarioNome: String = "",

    @NonNull
    @ColumnInfo(name = "usuario_email")
    var usuarioEmail: String = "",

    @NonNull
    @ColumnInfo(name = "usuario_senha")
    var usuarioSenha: String = "",

    @NonNull
    @ColumnInfo(name = "usuario_ativo")
    var usuarioAtivo: Int = 1,

    @NonNull
    @ColumnInfo(name = "usuario_sobre")
    var usuarioSobre: String = ""
)
