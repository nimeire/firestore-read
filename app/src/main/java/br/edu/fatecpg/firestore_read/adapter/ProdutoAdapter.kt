package br.edu.fatecpg.firestore_read.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.fatecpg.firestore_read.databinding.ItemProdutoBinding
import br.edu.fatecpg.firestore_read.model.Produto
import java.text.NumberFormat
import java.util.Locale

class ProdutoAdapter(
    private val listaProdutos: List<Produto>
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    private val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    inner class ProdutoViewHolder(val binding: ItemProdutoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val binding = ItemProdutoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProdutoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = listaProdutos[position]
        holder.binding.tvItemNome.text = produto.nome
        holder.binding.tvItemCategoria.text = "Categoria: ${produto.categoria}"
        holder.binding.tvItemPreco.text = formatoMoeda.format(produto.preco)
    }

    override fun getItemCount(): Int = listaProdutos.size
}