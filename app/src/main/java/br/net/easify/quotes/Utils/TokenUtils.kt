package br.net.easify.quotes.Utils

import android.content.Context
import br.net.easify.quotes.Database.AppDatabase
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class TokenUtils(context: Context) {

    private var db = AppDatabase.getAppDataBase(context)!!

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

                val current = Date()!!
                var parsedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiredAt)

                return (parsedDate > current)
            }
        }

        return false
    }
}
