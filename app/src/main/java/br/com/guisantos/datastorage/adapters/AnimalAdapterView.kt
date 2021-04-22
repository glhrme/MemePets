package br.com.guisantos.datastorage.adapters

import br.com.guisantos.datastorage.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.guisantos.datastorage.database.entities.Animal


class AnimalAdapterView(context: Context, animals: List<Animal>) : RecyclerView.Adapter<AnimalAdapterView.AnimalViewHolder>() {

    private val context: Context = context
    private val animals: List<Animal> = animals

    class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var animalName: TextView? = null


        init {
            animalName = itemView.findViewById(R.id.item_animal_name)
        }

        fun fillData() {
            animalName?.text = "Oi Mundo"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val viewAnimal = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false)
        return AnimalViewHolder(viewAnimal)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.fillData()
    }

    override fun getItemCount(): Int {
        return animals.size
    }
}
