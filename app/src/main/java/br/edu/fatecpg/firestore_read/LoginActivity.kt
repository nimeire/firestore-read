package br.edu.fatecpg.firestore_read

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.firestore_read.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            irParaMain()
        }

        // Login
        binding.btnEntrar.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                mostrarMensagem("Preencha e-mail e senha.")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, senha)
                .addOnSuccessListener { irParaMain() }
                .addOnFailureListener { e -> mostrarMensagem("Erro ao entrar: ${e.localizedMessage}") }
        }

        // Cadastro
        binding.btnCadastrar.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()

            if (email.isEmpty() || senha.length < 6) {
                mostrarMensagem("Informe um e-mail válido e senha com no mínimo 6 caracteres.")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener {
                    mostrarMensagem("Conta criada com sucesso!")
                    irParaMain()
                }
                .addOnFailureListener { e -> mostrarMensagem("Erro ao cadastrar: ${e.localizedMessage}") }
        }

        // Recuperar Senha
        binding.tvRecuperarSenha.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                mostrarMensagem("Digite seu e-mail acima para recuperar a senha.")
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener { mostrarMensagem("E-mail de redefinição enviado para $email.") }
                .addOnFailureListener { e -> mostrarMensagem("Falha ao enviar e-mail: ${e.localizedMessage}") }
        }
    }

    private fun irParaMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun mostrarMensagem(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}