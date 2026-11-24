package br.edu.fatecpg.firestore_read

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.fatecpg.firestore_read.adapter.ProdutoAdapter
import br.edu.fatecpg.firestore_read.databinding.ActivityListaProdutosBinding
import br.edu.fatecpg.firestore_read.model.Produto
import com.google.firebase.firestore.FirebaseFirestore

class ListaProdutosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaProdutosBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ProdutoAdapter
    private val listaProdutos = mutableListOf<Produto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        adapter = ProdutoAdapter(listaProdutos)
        binding.rvProdutos.layoutManager = LinearLayoutManager(this)
        binding.rvProdutos.adapter = adapter

        carregarProdutos()
    }

    private fun carregarProdutos() {
        db.collection("Produtos")
            .get()
            .addOnSuccessListener { documentos ->
                listaProdutos.clear()
                for (doc in documentos) {
                    val produto = doc.toObject(Produto::class.java).apply {
                        id = doc.id
                    }
                    listaProdutos.add(produto)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao buscar dados: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
}