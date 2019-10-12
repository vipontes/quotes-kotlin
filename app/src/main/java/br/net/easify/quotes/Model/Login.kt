package br.net.easify.quotes.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "token")
data class Login (

    @PrimaryKey
    @ColumnInfo(name = "token")
    @SerializedName("token")
    val token: String,

    @ColumnInfo(name = "refresh_token")
    @SerializedName("refresh_token")
    val refreshToken: String
)

data class LoginData (
    val email: String,
    val password: String,
    val device: String
)

@Entity(tableName = "token")
data class Token (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "token")
    val token: String,

    @ColumnInfo(name = "refresh_token")
    val refreshToken: String
)