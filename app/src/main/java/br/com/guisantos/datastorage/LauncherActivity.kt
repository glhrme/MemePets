package br.com.guisantos.datastorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.entities.Animal

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
    }

    override fun onResume() {
        super.onResume()
        val database = AnimalDatabase.getInstance(this)
        val dao = database?.getAnimalDao()
        dao?.create(Animal("Bailey"))
        dao?.create(Animal("Luna"))
        Log.i("animal", "" + dao?.getAll()?.get(0)?.animalName)
        Log.i("animal", "" + dao?.getAll()?.get(1)?.animalName)
        Log.i("animal", "" + dao?.getAll()?.get(2)?.animalName)
    }
}