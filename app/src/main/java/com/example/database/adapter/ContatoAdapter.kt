package com.example.database.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.database.databinding.ActivityMainBinding
import com.example.database.databinding.ContatoItemBinding
import com.example.database.model.Usuarios

class ContatoAdapter(private val context: Context, private val listaUsuarios: MutableList<Usuarios>):
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContatoViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.txtNome.text = listaUsuarios[position].nome
        holder.txtSobrenome.text = listaUsuarios[position].sobrenome
        holder.txtIdade.text = listaUsuarios[position].idade
        holder.txtcelular.text = listaUsuarios[position].celular
    }

    override fun getItemCount() = listaUsuarios.size

    inner class ContatoViewHolder(binding: ContatoItemBinding): RecyclerView.ViewHolder(binding.root) {

        val txtNome = binding.txtNome
        val txtSobrenome = binding.txtSobrenome
        val txtIdade = binding.txtIdade
        val txtcelular = binding.txtTelefone
    }
}