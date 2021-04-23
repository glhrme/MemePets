package br.com.guisantos.datastorage.database.dao

import androidx.room.*
import br.com.guisantos.datastorage.database.entities.Animal

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animal")
    fun getAll(): MutableList<Animal>

    @Query("SELECT * from animal where uid = :uid")
    fun getAnimal(uid: Int) : Animal

    @Insert
    fun create(animal: Animal)

    @Delete
    fun delete(animal: Animal)

    @Update
    fun update(animal: Animal)
}