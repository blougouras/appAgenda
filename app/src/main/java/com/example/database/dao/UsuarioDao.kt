package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.database.model.Usuarios

@Dao
interface UsuarioDao {

    @Insert
    fun inserir(lista: List<Usuarios>)

    @Query("SELECT * FROM tabelaUsuarios ORDER BY nome ASC")
    fun get(): MutableList<Usuarios>

    @Query("""
        UPDATE tabelaUsuarios 
        SET nome = :novoNome, 
            sobrenome = :novoSobrenome, 
            idade = :novaIdade, 
            celular = :novoCelular 
        WHERE uid = :id
    """)
    fun atualizar(
        id: Int,
        novoNome: String,
        novoSobrenome: String,
        novaIdade: String,
        novoCelular: String
    )

    @Delete
    fun deletar(usuarios: Usuarios)
}
