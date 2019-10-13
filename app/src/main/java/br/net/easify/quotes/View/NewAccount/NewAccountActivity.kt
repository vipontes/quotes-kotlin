package br.net.easify.quotes.View.NewAccount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.net.easify.quotes.Database.Entities.Token
import br.net.easify.quotes.Model.Login
import br.net.easify.quotes.R
import br.net.easify.quotes.ViewModel.NewAccountViewModel
import kotlinx.android.synthetic.main.activity_new_account.*

class NewAccountActivity : AppCompatActivity() {

    private lateinit var viewModel: NewAccountViewModel

    private val usuarioIdObserver = Observer<Int> { res: Int ->
        res.let {
            if ( it > 0 ) {
                finish()
            }
        }
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
        setContentView(R.layout.activity_new_account)

        viewModel = ViewModelProviders.of(this).get(NewAccountViewModel::class.java)
        viewModel.usuarioId.observe(this, usuarioIdObserver)
        viewModel.errorMessage.observe(this, errorMessageObserver)

        initializeLayout()
    }

    fun initializeLayout() {

        btnCreateAccount.setOnClickListener {

            val nome: String = txtNome.text.toString()
            val email: String = txtEmail.text.toString()
            val senha: String = txtSenha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, getString(R.string.new_account_required_fields), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.createNewAccount(nome, email, senha)
        }
    }
}
