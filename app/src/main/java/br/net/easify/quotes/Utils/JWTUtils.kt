package br.net.easify.quotes.Utils

import android.util.Base64
import org.json.JSONObject

class JWTUtils {

    fun decodeHeader(JWTEncoded: String): JSONObject {

        val parts = JWTEncoded.split(".")
        val base64EncodedBody = parts[0]
        var decodedBytes = Base64.decode(base64EncodedBody, Base64.URL_SAFE)
        return JSONObject(String(decodedBytes))
    }

    fun decodeBody(JWTEncoded: String): JSONObject {

        val parts = JWTEncoded.split(".")
        val base64EncodedBody = parts[1]
        var decodedBytes = Base64.decode(base64EncodedBody, Base64.URL_SAFE)
        return JSONObject(String(decodedBytes))
    }

    fun decodeSignature(JWTEncoded: String): JSONObject {

        val parts = JWTEncoded.split(".")
        val base64EncodedBody = parts[2]
        var decodedBytes = Base64.decode(base64EncodedBody, Base64.URL_SAFE)
        return JSONObject(String(decodedBytes))
    }
}
