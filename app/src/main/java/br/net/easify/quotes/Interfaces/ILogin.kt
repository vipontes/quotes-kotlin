package br.net.easify.quotes.Interfaces

import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.Model.LoginData
import br.net.easify.quotes.Model.RefreshTokenData
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ILogin {
    @POST("login")
    fun login(@Body data: LoginData): Single<Login>

    @POST("refresh-token")
    fun refreshToken(@Body data: RefreshTokenData): Single<Login>
}