package br.net.easify.quotes.View.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.Database.Entities.Token
import br.net.easify.quotes.R
import br.net.easify.quotes.Database.AppDatabase
import br.net.easify.quotes.Utils.JWTUtils
import br.net.easify.quotes.View.Main.MainActivity
import br.net.easify.quotes.View.NewAccount.NewAccountActivity
import br.net.easify.quotes.ViewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    private val tokensObserver = Observer<Login> {
        startMainActivity()
    }

    private val errorMessageObserver = Observer<String> { res: String ->
        res.let {
            if (!res.isEmpty()) {
                Toast.makeText(this, viewModel.errorMessage.value, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.tokens.observe(this, tokensObserver)
        viewModel.errorMessage.observe(this, errorMessageObserver)
        if ( !viewModel.validateToken() ) {
            viewModel.refreshToken()
        } else {
            startMainActivity()
            return
        }

        initializeLayout()
    }

    fun initializeLayout() {
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {

            val email: String = txtEmail.text.toString()
            val senha: String = txtSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, getString(R.string.login_required_fields), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val device = android.os.Build.DEVICE

            viewModel.login(email, senha, device)
        }

        txtCadastre.setOnClickListener {
            var newAccountActivity = Intent(this, NewAccountActivity::class.java)
            startActivity(newAccountActivity)
        }
    }

    fun startMainActivity() {
        var mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
        finish()
    }
}
