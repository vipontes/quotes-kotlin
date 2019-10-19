package br.net.easify.quotes.Model

data class QuoteReacao(
    val quoteId: Int?,
    val usuarioId: Int?,
    val reacaoId: Int?,
    val quoteReacaoData: String?,
    val reacaoDescricao: String?,
    val reacaoIcon: String?
)
