package br.com.guisantos.datastorage.adapters

import br.com.guisantos.datastorage.R
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.guisantos.datastorage.database.entities.Animal


class AnimalAdapterView(private val context: Context, private var animals: MutableList<Animal>) : RecyclerView.Adapter<AnimalAdapterView.AnimalViewHolder>() {

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

    fun removeAnimal(position: Int){
        animals.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getAnimal(position: Int): Animal {
        return animals[position]
    }

    private fun clearAnimals() {
        animals.clear()
    }

    private fun addAllAnimals(updatedAnimals: MutableList<Animal>) {
        animals = updatedAnimals
    }

    fun update(animals: MutableList<Animal>) {
        clearAnimals()
        addAllAnimals(animals)
        notifyDataSetChanged()
    }

    inner class AnimalViewHolder(itemView: View) : ViewHolder(itemView) {

        private var animalName: TextView? = null
        private var animalGender: TextView? = null


        init {
            animalName = itemView.findViewById(R.id.item_animal_name)
            animalGender = itemView.findViewById(R.id.item_animal_gender_content)
        }

        fun fillData(animal: Animal) {
            animalName?.text = animal.animalName
            animalGender?.text = Animal.genderValidator(animal?.animalGender)

        }
    }


}

