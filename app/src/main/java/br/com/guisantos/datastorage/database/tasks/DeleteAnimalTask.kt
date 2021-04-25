package br.com.guisantos.datastorage.database.tasks

import android.os.AsyncTask
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import java.net.URL

class DeleteAnimalTask(private val animalDao: AnimalDao, private val animal: Animal) : AsyncTask<URL?, Int?, Long?>() {
    override fun doInBackground(vararg params: URL?): Long? {
        animalDao.delete(animal)
        return null
    }
}