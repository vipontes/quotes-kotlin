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
    val usuarioId: Int = 0,

    @NonNull
    @ColumnInfo(name = "usuario_nome")
    val usuarioNome: String = "",

    @NonNull
    @ColumnInfo(name = "usuario_email")
    val usuarioEmail: String = "",

    @NonNull
    @ColumnInfo(name = "usuario_senha")
    val usuarioSenha: String = "",

    @NonNull
    @ColumnInfo(name = "usuario_ativo")
    val usuarioAtivo: Int = 1,

    @NonNull
    @ColumnInfo(name = "usuario_sobre")
    val usuarioSobre: String = ""
)
