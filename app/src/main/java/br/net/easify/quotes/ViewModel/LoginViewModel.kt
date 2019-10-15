package br.net.easify.quotes.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Database.Entities.Token
import br.net.easify.quotes.Database.Entities.UsuarioLogado
import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.Model.Usuario
import br.net.easify.quotes.Services.LoginService
import br.net.easify.quotes.Services.UsuarioService
import br.net.easify.quotes.Utils.JWTUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import com.google.gson.JsonParser
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginService = LoginService()
    private val usuarioService = UsuarioService()
    private val disposable = CompositeDisposable()

    private lateinit var db: AppDatabase

    val tokens by lazy { MutableLiveData<Login>() }
    val errorMessage by lazy { MutableLiveData<String>() }

    init {
        if ( !::db.isInitialized ) {
            db = AppDatabase.getAppDataBase(application)!!
        }
    }

    fun login(email: String, senha: String, device: String) {
        checkLogin(email, senha, device)
    }

    fun refreshToken() {
        val refreshToken = tokens.value?.refreshToken
        refreshToken?.let {
            checkRefreshToken(it)
        }
    }

    fun validateToken(): Boolean {
        var token = db.tokenDao().getToken()

        var tokenDecoded: JSONObject? = null
        token?.let {
            val decoder = JWTUtils()
            tokenDecoded = decoder.decodeBody(it.token)
        }

        if (tokenDecoded != null) {

            // Verifica a data de expiração do token
            var expiredAt = ""
            tokenDecoded?.get("expired_at")?.let {
                expiredAt = it.toString()
            }

            if (!expiredAt.isEmpty()) {

                val current = Date()
                var parsedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiredAt)

                return (parsedDate > current)
            }
        }

        return false
    }

    private fun checkLogin(email: String, senha: String, device: String) {

        disposable.add(
            loginService.login(email, senha, device)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Login>() {
                    override fun onSuccess(res: Login) {
                        tokens.value = res
                        errorMessage.value = ""

                        val token = Token(1, res.token, res.refreshToken)
                        db.tokenDao().insertOrUpdate(token)

                        saveUserData(res.token)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()

                        if (e is HttpException) {
                            val errorJsonString = e.response().errorBody()?.string()
                            val message = JsonParser().parse(errorJsonString)
                                .asJsonObject["message"]
                                .asString

                            errorMessage.value = message
                        } else {
                            errorMessage.value = "Internal error"
                        }
                    }
                })
        )
    }

    private fun saveUserData(token: String) {
        var tokenDecoded: JSONObject?
        token.let {
            val decoder = JWTUtils()
            tokenDecoded = decoder.decodeBody(token)
        }

        if (tokenDecoded != null) {
            var usuarioId = 0
            tokenDecoded?.get("usuarioId")?.let {
                usuarioId = it.toString().toInt()
            }

            if (usuarioId > 0) {
                disposable.add(
                    usuarioService.getUser(usuarioId, "Bearer $token")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Usuario>() {
                            override fun onSuccess(usuario: Usuario) {
                                var usuarioLogado = UsuarioLogado(
                                    usuario.usuarioId,
                                    usuario.usuarioNome,
                                    usuario.usuarioEmail,
                                    usuario.usuarioSenha,
                                    usuario.usuarioAtivo,
                                    usuario.usuarioSobre
                                )

                                db.usuarioDao().insertUsuario(usuarioLogado)
                            }

                            override fun onError(e: Throwable) {
                                e.printStackTrace()

                                Log.e("Error", e.localizedMessage)
                            }
                        })
                )
            }
        }
    }

    private fun checkRefreshToken(refreshToken: String) {
        disposable.add(
            loginService.refreshToken(refreshToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Login>() {
                    override fun onSuccess(res: Login) {
                        tokens.value = res
                        errorMessage.value = ""
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()

                        if (e is HttpException) {
                            val errorJsonString = e.response().errorBody()?.string()
                            val message = JsonParser().parse(errorJsonString)
                                .asJsonObject["message"]
                                .asString

                            errorMessage.value = message
                        } else {
                            errorMessage.value = "Internal error"
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
