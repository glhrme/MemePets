package br.com.guisantos.datastorage.database.tasks

import android.os.AsyncTask
import android.util.Log
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import br.com.guisantos.datastorage.views.AnimalView
import java.net.URL


class SearchAnimalsTask(private val dao: AnimalDao, private val animalView: AnimalView) : AsyncTask<URL?, Int?, MutableList<Animal>>() {

    override fun doInBackground(vararg params: URL?): MutableList<Animal>? {
        return dao.getAll()
    }

    override fun onPostExecute(result: MutableList<Animal>?) {
        super.onPostExecute(result)
        if (result != null)
            animalView.uptadeAdapter(result)
    }
}