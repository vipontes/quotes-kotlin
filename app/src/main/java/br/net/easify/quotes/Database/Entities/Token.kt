package br.net.easify.quotes.Database.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token")
data class Token (

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @NonNull
    @ColumnInfo(name = "token")
    val token: String,

    @NonNull
    @ColumnInfo(name = "refresh_token")
    val refreshToken: String
)