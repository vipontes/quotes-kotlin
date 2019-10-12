package br.net.easify.quotes.Interfaces

import androidx.room.Dao
import androidx.room.Insert
import br.net.easify.quotes.Model.Token

@Dao
interface LoginDao {

    @Insert
    fun addToken(token: Token)
}