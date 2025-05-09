package com.example.database

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.database.databinding.ActivityCadastroUsuariosBinding
import com.example.database.model.Usuarios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast
import com.example.database.dao.UsuarioDao


class CadastroUsuarios : AppCompatActivity() {
    lateinit var button2: Button
    private lateinit var binding: ActivityCadastroUsuariosBinding
    private var usuarioDao: UsuarioDao? = null
    private var listaUsuarios: MutableList<Usuarios> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button2 = findViewById(R.id.voltar)
        button2.setOnClickListener {
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
        }

        binding.button3.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val nome = binding.editNome.text.toString()
                val sobrenome = binding.editSobrenome.text.toString()
                val idade = binding.editIdade.text.toString()
                val celular = binding.editCelular.text.toString()
                val mensagem: Boolean

                if(nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty()){
                    mensagem = false

                }else{
                    mensagem = true
                    cadastrar(nome,sobrenome, idade, celular)
                }
                withContext(Dispatchers.Main){
                    if(mensagem){
                        Toast.makeText(applicationContext,"Cadastro realizado com sucesso!!!", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Preencher todos os campos", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }


    }
    private fun cadastrar(nome: String, sobrenome: String, idade: String, celular: String)
    {
        val usuario = Usuarios(nome, sobrenome, idade,celular)
        listaUsuarios.add(usuario)
        usuarioDao = AppDataBase.getInstance(this).usuarioDao()
        usuarioDao!!.inserir(listaUsuarios)

    }
}