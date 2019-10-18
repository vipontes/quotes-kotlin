package br.net.easify.quotes.Services

import android.content.Context
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Interfaces.IUsuario
import br.net.easify.quotes.Model.Usuario
import br.net.easify.quotes.Utils.Constants
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UsuarioService(context: Context) {

    var db = AppDatabase.getAppDataBase(context)!!

    private val api = Retrofit.Builder()
        .baseUrl(Constants.apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(createClientAuth())
        .build()
        .create(IUsuario::class.java)

    private fun createClientAuth(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        val tokens = db.tokenDao().getToken()
        okHttpClientBuilder.addInterceptor(tokens?.let { AuthenticationInterceptorRefreshToken(it) })
        return okHttpClientBuilder.build()
    }

    fun insertUser(usuario: Usuario): Single<Usuario> {
        return api.insertUser(usuario)
    }

    fun getUser(usuarioId: Int): Single<Usuario> {
        return api.getUser(usuarioId)
    }
}
