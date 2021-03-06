package br.com.guisantos.datastorage.adapters

import br.com.guisantos.datastorage.R
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.guisantos.datastorage.database.entities.Animal
import br.com.guisantos.datastorage.utils.UtilsUri
import br.com.guisantos.datastorage.views.AnimalView
import java.io.File


class AnimalAdapterView(private val context: Context) : RecyclerView.Adapter<AnimalAdapterView.AnimalViewHolder>() {
    private var animals: MutableList<Animal> = arrayListOf()


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
        private var animalImage: ImageView? = null

        init {
            animalName = itemView.findViewById(R.id.item_animal_name)
            animalGender = itemView.findViewById(R.id.item_animal_gender_content)
            animalImage = itemView.findViewById(R.id.item_animal_image_view)
        }

        fun fillData(animal: Animal) {
            animalName?.text = animal.animalName
            animalGender?.text = Animal.genderValidator(animal?.animalGender)
            if(animal.imageString != Animal.EMPTY) {
                AnimalView.addImageAnimalView(animalImage!!, animal.imageString!!)
            }
        }
    }


}

