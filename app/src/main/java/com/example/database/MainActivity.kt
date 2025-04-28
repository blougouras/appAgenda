package com.example.database

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

class MainActivity : AppCompatActivity() {
    lateinit var button1: Button
    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioDao: UsuarioDao
    private lateinit var contatoAdapter: ContatoAdapter
    private val _listaUsuario = MutableLiveData<MutableList<Usuarios>>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button1 = findViewById(R.id.button)
        button1.setOnClickListener {
            val intent = Intent(
                this,
                CadastroUsuarios::class.java
            )
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.IO).launch {
            getContatos()

            withContext(Dispatchers.Main){

                _listaUsuario.observe(this@MainActivity){ listaUsuario ->

                    val recyclerViewContato = binding.recyclerViewContatos
                    recyclerViewContato.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContato.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuario)
                    recyclerViewContato.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()
                }
            }
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {
            getContatos()

            withContext(Dispatchers.Main){

                _listaUsuario.observe(this@MainActivity){ listaUsuario ->

                    val recyclerViewContato = binding.recyclerViewContatos
                    recyclerViewContato.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContato.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuario)
                    recyclerViewContato.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()
                }
            }
        }

    }



    private fun getContatos(){
        usuarioDao = AppDataBase.getInstance(this).usuarioDao()
        val listaUsuarios: MutableList<Usuarios> = usuarioDao.get()
        _listaUsuario.postValue(listaUsuarios)
    }
}
