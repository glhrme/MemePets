package br.com.guisantos.datastorage.database.tasks

import android.os.AsyncTask
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal

class GetAnimalTask(private val dao: AnimalDao, private val id: Int, private val listener: GetAnimalListener) : AsyncTask<Void?, Void?, Animal>() {

    override fun doInBackground(vararg params: Void?): Animal {
        return dao.getAnimal(id);
    }

    override fun onPostExecute(result: Animal?) {
        super.onPostExecute(result)
        listener.onGetAnimal(result)
    }

    interface GetAnimalListener {
        fun onGetAnimal(result: Animal?)
    }
}