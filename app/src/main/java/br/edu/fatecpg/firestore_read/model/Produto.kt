package br.edu.fatecpg.firestore_read.model

data class Produto(
    var id: String = "",
    val nome: String = "",
    val categoria: String = "",
    val preco: Double = 0.0
)