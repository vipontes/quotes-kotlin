package br.net.easify.quotes.Model

data class Quote(
    val quoteId: Int?,
    val usuarioId: Int?,
    val quoteDataCriacao: String?,
    val quoteConteudo: String?,
    val quoteConteudoOfensivo: Int?,
    val quoteUsuarioConteudoOfensivoId: Int?,
    val usuarioNome: String?,
    val usuarioDenunciaNome: String?,
    val quoteGostei: Int?,
    val quoteNaoGostei: Int?
)