package br.net.easify.quotes.Interfaces

import br.net.easify.quotes.Model.ApiResult
import br.net.easify.quotes.Model.Usuario
import io.reactivex.Single
import retrofit2.http.*

interface IUsuario {
    @POST("usuario")
    fun insertUser(@Body data: Usuario): Single<Usuario>

    @PUT("usuario")
    fun updateUser(@Body data: Usuario): Single<ApiResult>

    @GET("usuario/{usuarioId}")
    fun getUser(@Path("usuarioId") usuarioId: Int): Single<Usuario>
}