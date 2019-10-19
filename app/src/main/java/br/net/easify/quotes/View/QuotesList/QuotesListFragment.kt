package br.net.easify.quotes.View.QuotesList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import br.net.easify.quotes.Model.Quote
import br.net.easify.quotes.R
import br.net.easify.quotes.View.Login.LoginActivity
import br.net.easify.quotes.ViewModel.QuoteListViewModel
import kotlinx.android.synthetic.main.fragment_quotes_list.*

class QuotesListFragment : Fragment() {

    private lateinit var viewModel: QuoteListViewModel
    private lateinit var listAdapter: QuoteListAdapter
    private lateinit var navController: NavController

    private val quoteListDataObserver = Observer<List<Quote>> { list: List<Quote> ->
        list?.let {
            quoteList.visibility = View.VISIBLE
            listAdapter.update(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading: Boolean ->
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            listError.visibility = View.GONE
            quoteList.visibility = View.GONE
        }
    }

    private val loadErrorLiveDataObserver = Observer<Boolean> { isError: Boolean ->
        listError.visibility = if (isError) View.VISIBLE else View.GONE
    }

    private val reacaoDataObserver = Observer<Boolean> {
        it?.let {
            if (it) {
                viewModel.refresh()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quotes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProviders.of(this).get(QuoteListViewModel::class.java)
        viewModel.quotes.observe(this, quoteListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, loadErrorLiveDataObserver)
        viewModel.reacao.observe(this, reacaoDataObserver)

        viewModel.refresh()

        listAdapter = QuoteListAdapter(viewModel, arrayListOf())

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

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId

        if (id == R.id.menuProfile) {
            val action = QuotesListFragmentDirections.actionProfile()
            navController.navigate(action)
        } else if (id == R.id.menuExit) {
            viewModel.logout()
            var loginActivity = Intent(context, LoginActivity::class.java)
            startActivity(loginActivity)
            getActivity()?.finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
