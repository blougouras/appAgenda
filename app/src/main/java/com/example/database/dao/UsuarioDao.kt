package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.model.Usuarios

@Dao
interface UsuarioDao {
    @Insert
    fun inserir(list: MutableList<Usuarios>)

    @Query("SELECT * FROM tabelaUsuarios ORDER BY nome ASC")
    fun get(): MutableList<Usuarios>
}