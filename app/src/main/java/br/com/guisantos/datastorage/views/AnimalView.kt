package br.com.guisantos.datastorage.views

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.guisantos.datastorage.adapters.AnimalAdapterView
import br.com.guisantos.datastorage.database.entities.Animal

class AnimalView(
    private val recyclerAnimal: RecyclerView,
    private val adapter: AnimalAdapterView,
    private val layout: RecyclerView.LayoutManager
) {

    private fun adapterConfig(view: RecyclerView) {
        view.adapter = adapter
        view.layoutManager = layout
    }

    fun init() {
        adapterConfig(recyclerAnimal)
    }
}