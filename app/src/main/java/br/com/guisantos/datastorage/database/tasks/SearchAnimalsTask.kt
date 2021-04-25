package br.com.guisantos.datastorage.database.tasks

import android.os.AsyncTask
import br.com.guisantos.datastorage.activities.LauncherActivity
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import java.net.URL


class SearchAnimalsTask(private val dao: AnimalDao, private val listener: AnimalsFoundListener) : AsyncTask<URL?, Int?, MutableList<Animal>>() {

    override fun doInBackground(vararg params: URL?): MutableList<Animal>? {
        return dao.getAll()
    }

    override fun onPostExecute(result: MutableList<Animal>?) {
        super.onPostExecute(result)
        if (result != null)
            listener.whenFound(result)
    }

    interface AnimalsFoundListener {
        fun whenFound(animals: MutableList<Animal>)
    }
}