package br.net.easify.quotes.Interfaces

import br.net.easify.quotes.Model.Usuario
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IUsuario {
    @POST("usuario")
    fun insertUser(@Body data: Usuario): Single<Usuario>

    @GET("usuario/{usuarioId}")
    fun getUser(@Path("usuarioId") usuarioId: Int): Single<Usuario>
}