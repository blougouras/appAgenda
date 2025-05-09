package com.example.database

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.adapter.ContatoAdapter
import com.example.database.dao.UsuarioDao
import com.example.database.databinding.ActivityMainBinding
import com.example.database.model.Usuarios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), ContatoAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioDao: UsuarioDao
    private lateinit var contatoAdapter: ContatoAdapter
    private val _listaUsuario = MutableLiveData<MutableList<Usuarios>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botão para ir à tela de cadastro
        binding.button.setOnClickListener {
            val intent = Intent(this, CadastroUsuarios::class.java)
            startActivity(intent)
        }

        // Setup RecyclerView com o adapter atualizado
        contatoAdapter = ContatoAdapter(this, mutableListOf(), this)
        binding.recyclerViewContatos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = contatoAdapter
        }

        // Observa a lista de usuários
        _listaUsuario.observe(this) { listaUsuario ->
            contatoAdapter.updateList(listaUsuario)
        }

        carregarContatos()
    }

    override fun onResume() {
        super.onResume()
        carregarContatos() // Atualiza lista ao voltar
    }

    private fun carregarContatos() {
        CoroutineScope(Dispatchers.IO).launch {
            usuarioDao = AppDataBase.getInstance(this@MainActivity).usuarioDao()
            val listaUsuarios = usuarioDao.get()
            _listaUsuario.postValue(listaUsuarios)
        }
    }

    // Clique no botão de atualizar
    override fun onAtualizarClick(usuario: Usuarios) {
        val intent = Intent(this, AtualizarUsuario::class.java).apply {
            putExtra("uid", usuario.uid)
            putExtra("nome", usuario.nome)
            putExtra("sobrenome", usuario.sobrenome)
            putExtra("idade", usuario.idade)
            putExtra("celular", usuario.celular)
        }
        startActivity(intent)
    }

    // Clique no botão de deletar
    override fun onDeletarClick(usuario: Usuarios) {
        CoroutineScope(Dispatchers.IO).launch {
            usuarioDao = AppDataBase.getInstance(this@MainActivity).usuarioDao()
            usuarioDao.deletar(usuario)

            val novaLista = usuarioDao.get()
            withContext(Dispatchers.Main) {
                _listaUsuario.postValue(novaLista)
            }
        }
    }
}
