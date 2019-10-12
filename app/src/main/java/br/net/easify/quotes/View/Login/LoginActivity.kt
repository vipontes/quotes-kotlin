package br.net.easify.quotes.View.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.R
import br.net.easify.quotes.ViewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    private val tokensObserver = Observer<Login> {res: Login ->
        res?.let {

        }
    }

    private val errorMessageObserver = Observer<String> {res: String ->
        res?.let {
            if ( !res.isEmpty() ) {
                Toast.makeText(this, viewModel.errorMessage.value, Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.tokens.observe(this, tokensObserver)
        viewModel.errorMessage.observe(this, errorMessageObserver)

        btnLogin.setOnClickListener {

            val email: String = txtEmail.text.toString()
            val senha: String = txtSenha.text.toString()

            if ( email.isEmpty() || senha.isEmpty() ) {
                Toast.makeText(this, getString(R.string.login_required_fields), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val device = android.os.Build.DEVICE

            viewModel.login(email, senha, device)
        }
    }
}
