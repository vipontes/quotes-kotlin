package br.net.easify.quotes.View.QuotesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.net.easify.quotes.Model.Quote
import br.net.easify.quotes.R
import kotlinx.android.synthetic.main.quote_item.view.*

class QuoteListAdapter(private val quoteList: ArrayList<Quote>): RecyclerView.Adapter<QuoteListAdapter.QuoteViewHolder>() {

    fun update(newQuoteList: List<Quote>) {
        quoteList.clear()
        quoteList.addAll(newQuoteList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.quote_item, parent, false)
        return QuoteViewHolder(view)
    }

    override fun getItemCount() = quoteList.size

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.view.description.text = quoteList[position].quoteConteudo
//        holder.view.animalLayout.setOnClickListener {
//            val action = ListFragmentDirections.actionDetail(animalList[position])
//            Navigation.findNavController(holder.view).navigate(action)
//        }
    }

    class QuoteViewHolder(var view: View): RecyclerView.ViewHolder(view)
}
