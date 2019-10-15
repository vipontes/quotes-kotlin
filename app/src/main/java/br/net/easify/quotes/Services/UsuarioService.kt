package br.net.easify.quotes.Services

import android.content.Context
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Interfaces.IUsuario
import br.net.easify.quotes.Model.Usuario
import br.net.easify.quotes.Utils.Constants
import io.reactivex.Single
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException


class UsuarioService {

    private val api = Retrofit.Builder()
        .baseUrl(Constants.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(IUsuario::class.java)

    fun insertUser(usuario: Usuario): Single<Usuario> {
        return api.insertUser(usuario)
    }

    fun getUser(usuarioId: Int, token: String): Single<Usuario> {
        return api.getUser(usuarioId, token)
    }
}
