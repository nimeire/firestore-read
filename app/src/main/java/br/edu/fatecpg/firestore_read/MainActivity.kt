package br.edu.fatecpg.firestore_read

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.firestore_read.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.btnSalvarProduto.setOnClickListener { salvarProduto() }

        binding.btnVerLista.setOnClickListener {
            startActivity(Intent(this, ListaProdutosActivity::class.java))
        }

        binding.btnSair.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun salvarProduto() {
        val nome = binding.etNome.text.toString().trim()
        val categoria = binding.etCategoria.text.toString().trim()
        val precoTexto = binding.etPreco.text.toString().trim()

        if (nome.isEmpty() || categoria.isEmpty() || precoTexto.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        val preco = precoTexto.replace(",", ".").toDoubleOrNull()
        if (preco == null) {
            Toast.makeText(this, "Informe um preço válido.", Toast.LENGTH_SHORT).show()
            return
        }

        val dadosProduto = hashMapOf(
            "nome" to nome,
            "categoria" to categoria,
            "preco" to preco
        )

        db.collection("Produtos")
            .add(dadosProduto)
            .addOnSuccessListener {
                Toast.makeText(this, "Produto cadastrado!", Toast.LENGTH_SHORT).show()
                binding.etNome.text?.clear()
                binding.etCategoria.text?.clear()
                binding.etPreco.text?.clear()
                binding.etNome.requestFocus()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
}