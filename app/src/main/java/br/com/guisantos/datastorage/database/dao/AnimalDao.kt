package br.com.guisantos.datastorage.database.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.guisantos.datastorage.database.entities.Animal

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animal")
    fun getAll(): List<Animal>
}