package com.example.database

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.database.dao.UsuarioDao
import com.example.database.databinding.ActivityAtualizarCadastroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtualizarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarCadastroBinding
    private lateinit var usuarioDao: UsuarioDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuarioDao = AppDataBase.getInstance(this).usuarioDao()

        val nomeRecuperado = intent.extras?.getString("nome")
        val sobrenomeRecuperado = intent.extras?.getString("sobrenome")
        val idadeRecuperada = intent.extras?.getString("idade")
        val celularRecuperado = intent.extras?.getString("celular")
        val uid = intent.extras!!.getInt("uid")

        binding.editNome.setText(nomeRecuperado)
        binding.editSobrenome.setText(sobrenomeRecuperado)
        binding.editIdade.setText(idadeRecuperada)
        binding.editCelular.setText(celularRecuperado)

        binding.btAtualizar.setOnClickListener {
            val nome = binding.editNome.text.toString().trim()
            val sobrenome = binding.editSobrenome.text.toString().trim()
            val idade = binding.editIdade.text.toString().trim()
            val celular = binding.editCelular.text.toString().trim()

            if (nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty()) {
                Toast.makeText(this, "Preencher todos os campos!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idadeInt = idade.toIntOrNull()
            val celularLong = celular.toLongOrNull()

            if (idadeInt == null || celularLong == null) {
                Toast.makeText(this, "Idade ou celular inválido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                atualizarContato(uid, nome, sobrenome, idade, celular)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AtualizarUsuario, "Atualizado com sucesso!!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun atualizarContato(uid: Int, nome: String, sobrenome: String, idade: String, celular: String) {
        usuarioDao.atualizar(uid, nome, sobrenome, idade, celular)
    }
}
