package br.net.easify.quotes.Database.DAO

import androidx.room.*
import br.net.easify.quotes.Database.Entities.Token

@Dao
interface ITokenDao {

    @Query("SELECT id, token, refresh_token FROM token WHERE id = 1")
    fun getToken(): Token?

    @Query("DELETE FROM token WHERE id = 1")
    fun deleteToken()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(token: Token): Long

    @Update()
    fun update(token: Token)

    @Transaction
    fun insertOrUpdate(token: Token) {
        if (insertIgnore(token) == -1L) {
            update(token)
        }
    }
}
