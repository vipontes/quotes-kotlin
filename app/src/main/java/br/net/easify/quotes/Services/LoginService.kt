package br.net.easify.quotes.Services

import br.net.easify.quotes.Interfaces.ILogin
import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.Model.LoginData
import br.net.easify.quotes.Model.RefreshTokenData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LoginService {

    private val API_URL = "http://quotes.easify.info/v1/"

    private val api = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ILogin::class.java)

    fun login(email: String, senha: String, device: String): Single<Login> {

        val loginData = LoginData(email, senha, device)
        return api.login(loginData)
    }

    fun refreshToken(refreshToken: String): Single<Login> {

        val refreshToken = RefreshTokenData(refreshToken)
        return api.refreshToken(refreshToken)
    }


}