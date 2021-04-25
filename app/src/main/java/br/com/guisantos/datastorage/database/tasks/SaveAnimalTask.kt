package br.com.guisantos.datastorage.database.tasks

import android.os.AsyncTask
import android.util.Log
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal

class SaveAnimalTask(private val animal: Animal, private val dao: AnimalDao, private val listener: AnimalSavedListener) : AsyncTask<Void, Void, Void?>() {

    override fun doInBackground(vararg params: Void?): Void? {
        dao.create(animal)
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        listener.onAnimalSavedListener()
        Log.i("teste", "entrou")
    }

    interface AnimalSavedListener {
        fun onAnimalSavedListener()
    }

}