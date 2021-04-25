package br.com.guisantos.datastorage.database.tasks

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal

class UptadeAnimalTask(private val dao: AnimalDao, private val animal: Animal, private val listener: UpdateAnimalListener) : AsyncTask<Void?, Void?, Void?>() {
    override fun doInBackground(vararg params: Void?): Void? {
        dao.update(animal)
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        listener.onUpdateAnimal()
    }

    interface UpdateAnimalListener {
        fun onUpdateAnimal()
    }
}