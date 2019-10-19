package br.net.easify.quotes.Database.DAO

import androidx.room.*
import br.net.easify.quotes.Database.Entities.Token
import br.net.easify.quotes.Database.Entities.UsuarioLogado

@Dao
interface IUsuarioDao {
    @Query("SELECT usuario_id, " +
            "usuario_nome, " +
            "usuario_email, " +
            "usuario_senha, " +
            "usuario_ativo, " +
            "usuario_sobre " +
            "FROM usuario " +
            "WHERE usuario_id = :usuarioId"
    )
    fun getUsuario(usuarioId: Int): UsuarioLogado?

    @Query("DELETE FROM usuario")
    fun deleteUsuario()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsuario(usuario: UsuarioLogado): Long
}
