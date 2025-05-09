package com.example.database.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.database.databinding.ContatoItemBinding
import com.example.database.model.Usuarios

class ContatoAdapter(
    private val context: Context,
    private var listaUsuarios: MutableList<Usuarios>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    interface OnItemClickListener {
        fun onAtualizarClick(usuario: Usuarios)
        fun onDeletarClick(usuario: Usuarios)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContatoViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val usuario = listaUsuarios[position]

        holder.txtNome.text = usuario.nome
        holder.txtSobrenome.text = usuario.sobrenome
        holder.txtIdade.text = usuario.idade
        holder.txtCelular.text = usuario.celular

        holder.btAtualiza.setOnClickListener {
            listener.onAtualizarClick(usuario)
        }

        holder.btDeleta.setOnClickListener {
            listener.onDeletarClick(usuario)
        }
    }

    override fun getItemCount() = listaUsuarios.size

    fun updateList(novaLista: MutableList<Usuarios>) {
        listaUsuarios.clear()
        listaUsuarios.addAll(novaLista)
        notifyDataSetChanged()
    }

    inner class ContatoViewHolder(binding: ContatoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtNome = binding.txtNome
        val txtSobrenome = binding.txtSobrenome
        val txtIdade = binding.txtIdade
        val txtCelular = binding.txtTelefone
        val btAtualiza = binding.btAtualiza
        val btDeleta = binding.btdeleta
    }
}
