package br.com.guisantos.datastorage.views

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.guisantos.datastorage.adapters.AnimalAdapterView
import br.com.guisantos.datastorage.database.entities.Animal

class AnimalView(animals: List<Animal>, recyclerAnimal: RecyclerView, context: Context) {
    private val animals = animals
    private val context = context


    private fun adapterConfig(view: RecyclerView) {
        view.adapter = AnimalAdapterView(context, animals)
        view.layoutManager = LinearLayoutManager(context)
    }

    init {
        adapterConfig(recyclerAnimal)
    }
}