package br.net.easify.quotes.View.NewQuote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import br.net.easify.quotes.R
import br.net.easify.quotes.ViewModel.NewQuoteViewModel
import kotlinx.android.synthetic.main.fragment_new_quote.*



class NewQuoteFragment : Fragment() {

    private lateinit var viewModel: NewQuoteViewModel

    private val quoteIdLiveDataObserver = Observer<Int> { quoteId: Int ->
        if ( quoteId > 0 ) {
            val action = NewQuoteFragmentDirections.actionQuotesList()
            Navigation.findNavController(getView()!!).navigate(action)
        }
    }

    private val errorLiveDataObserver = Observer<Boolean> { error: Boolean ->

        if ( error ) {
            Toast.makeText(context, getString(R.string.quote_save_error), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_quote, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NewQuoteViewModel::class.java)
        viewModel.quoteId.observe(this, quoteIdLiveDataObserver)
        viewModel.error.observe(this, errorLiveDataObserver)

        btnSave.setOnClickListener {

            var content: String = quoteContent.text.toString()

            if (content.isEmpty() ) {
                Toast.makeText(context, getString(R.string.quote_content_error), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.insertQuote(content)
        }
    }
}
