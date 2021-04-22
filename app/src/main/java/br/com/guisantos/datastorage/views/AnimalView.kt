package br.com.guisantos.datastorage.views

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.guisantos.datastorage.adapters.AnimalAdapterView
import br.com.guisantos.datastorage.database.entities.Animal

class AnimalView(private val animals: List<Animal>, private val recyclerAnimal: RecyclerView, private val context: Context) {

    private fun adapterConfig(view: RecyclerView) {
        view.adapter = AnimalAdapterView(context , animals)
        view.layoutManager = LinearLayoutManager(context)
    }

    fun init() {
        adapterConfig(recyclerAnimal)
    }
}