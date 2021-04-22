package br.com.guisantos.datastorage.adapters

import br.com.guisantos.datastorage.R
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.guisantos.datastorage.database.entities.Animal


class AnimalAdapterView(private val context: Context, private val animals: List<Animal>) : RecyclerView.Adapter<AnimalAdapterView.AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {

        val viewAnimal = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false)
        return AnimalViewHolder(viewAnimal)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        var animal: Animal = animals[position]
        holder.fillData(animal)
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    inner class AnimalViewHolder(itemView: View) : ViewHolder(itemView) {

        private var animalName: TextView? = null


        init {
            animalName = itemView.findViewById(R.id.item_animal_name)
        }

        fun fillData(animal: Animal) {
            animalName?.text = animal.animalName
        }
    }


}
