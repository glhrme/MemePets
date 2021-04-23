package br.com.guisantos.datastorage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import br.com.guisantos.datastorage.database.migrations.AnimalMigrations.Companion.MIGRATION_1_2

@Database(entities = [Animal::class], version = 2)
abstract class AnimalDatabase : RoomDatabase() {

    abstract fun getAnimalDao() : AnimalDao

    companion object {
        val DATABASE_NAME: String = "animal.db"
        fun getInstance(context: Context?): AnimalDatabase? {
            return Room
                .databaseBuilder(context!!, AnimalDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }

}