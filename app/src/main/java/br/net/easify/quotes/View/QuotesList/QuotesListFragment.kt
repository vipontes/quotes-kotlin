package br.net.easify.quotes.View.QuotesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import br.net.easify.quotes.Model.Quote

import br.net.easify.quotes.R
import br.net.easify.quotes.ViewModel.QuoteListViewModel
import kotlinx.android.synthetic.main.fragment_quotes_list.*

class QuotesListFragment : Fragment() {

    private lateinit var viewModel: QuoteListViewModel
    private var listAdapter = QuoteListAdapter(arrayListOf())

    private val quoteListDataObserver = Observer<List<Quote>> { list: List<Quote> ->
        list?.let {
            quoteList.visibility = View.VISIBLE
            listAdapter.update(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading: Boolean ->
        loadingView.visibility = if(isLoading) View.VISIBLE else View.GONE
        if ( isLoading ) {
            listError.visibility = View.GONE
            quoteList.visibility = View.GONE
        }
    }

    private val loadErrorLiveDataObserver = Observer<Boolean> { isError: Boolean ->
        listError.visibility = if(isError) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(QuoteListViewModel::class.java)
        viewModel.quotes.observe(this, quoteListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, loadErrorLiveDataObserver)
        viewModel.refresh()

        quoteList.apply {

            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {

            quoteList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        buttonAddQuote.setOnClickListener {
            val action = QuotesListFragmentDirections.actionNewQuote()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
