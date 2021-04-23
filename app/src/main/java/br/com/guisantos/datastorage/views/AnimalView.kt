package br.com.guisantos.datastorage.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.guisantos.datastorage.activities.FormAnimalActivity
import br.com.guisantos.datastorage.adapters.AnimalAdapterView
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import java.io.Serializable

class AnimalView(
        private val recyclerAnimal: RecyclerView,
        private val adapter: AnimalAdapterView,
        private val layout: RecyclerView.LayoutManager,
        private val context: Context,
        private val animalDao: AnimalDao
) {

    private fun adapterConfig(view: RecyclerView) {
        view.adapter = adapter
        view.layoutManager = layout
    }

    private fun movementsConfig(view: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                var swipeMovements = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(0, swipeMovements)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    adapter.removeAnimal(viewHolder.adapterPosition)
                    animalDao.delete(adapter.getAnimal(viewHolder.adapterPosition))
                } else
                    context.startActivity(Intent(context, FormAnimalActivity::class.java).putExtra("uidAnimal", adapter.getAnimal(viewHolder.adapterPosition).uid))
            }
        })
        itemTouchHelper.attachToRecyclerView(view)
    }

    fun init() {
        adapterConfig(recyclerAnimal)
        movementsConfig(recyclerAnimal)
    }
}