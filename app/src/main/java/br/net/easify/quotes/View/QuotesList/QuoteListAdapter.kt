package br.net.easify.quotes.View.QuotesList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import br.net.easify.quotes.Model.Quote
import br.net.easify.quotes.R
import br.net.easify.quotes.Utils.Constants
import br.net.easify.quotes.ViewModel.QuoteListViewModel
import kotlinx.android.synthetic.main.quote_item.view.*

class QuoteListAdapter(private val viewModel: QuoteListViewModel,
                       private val quoteList: ArrayList<Quote>)
    : RecyclerView.Adapter<QuoteListAdapter.QuoteViewHolder>() {

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
        holder.view.author.text = quoteList[position].usuarioNome
        holder.view.gosteiQtd.text = quoteList[position].quoteGostei.toString()
        holder.view.naoGosteiQtd.text = quoteList[position].quoteNaoGostei.toString()
    }

    inner class QuoteViewHolder(var view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        protected var gostei: LinearLayout
        protected var naoGostei: LinearLayout

        init {
            gostei = itemView.findViewById(R.id.gostei) as LinearLayout
            naoGostei = itemView.findViewById(R.id.naoGostei) as LinearLayout
            gostei.setOnClickListener(this)
            naoGostei.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val item = this.adapterPosition

            var usuarioId = viewModel.getUserId()
            val quoteId: Int = quoteList[item].quoteId!!

            if (v!!.id == gostei.id) {
                viewModel.reacao(usuarioId, quoteId, Constants.reacaoGostei)
            } else if (v.id == naoGostei.id)  {
                viewModel.reacao(usuarioId, quoteId, Constants.reacaoNaoGostei)
            }
        }
    }
}
