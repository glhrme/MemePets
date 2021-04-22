package br.com.guisantos.datastorage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal

@Database(entities = [Animal::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun getAnimalDao() : AnimalDao

}