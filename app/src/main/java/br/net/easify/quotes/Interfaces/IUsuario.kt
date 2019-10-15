package br.net.easify.quotes.Interfaces

import br.net.easify.quotes.Model.Usuario
import io.reactivex.Single
import retrofit2.http.*

interface IUsuario {
    @POST("usuario")
    fun insertUser(@Body data: Usuario): Single<Usuario>

    @GET("usuario/{usuarioId}")
    fun getUser(@Path("usuarioId") usuarioId: Int,
                @Header("Authorization") token: String): Single<Usuario>
}