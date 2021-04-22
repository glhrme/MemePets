package br.com.guisantos.datastorage.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.com.guisantos.datastorage.R
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal


class FormAnimalActivity : AppCompatActivity() {
    private var database: AnimalDatabase? = null
    private var dao: AnimalDao? = null
    private var animalName: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_animal)
        database = AnimalDatabase.getInstance(this)
        dao = database?.getAnimalDao()
    }

    override fun onResume() {
        animalName = findViewById(R.id.ac_form_animal_name)
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ac_form_menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.ac_form_menu_cancel) {
            finish()
        } else {
            dao?.create(Animal(animalName!!.text.toString()))
            Toast.makeText(this, "Salvar", Toast.LENGTH_LONG).show()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        database?.close()
        super.finish()
    }
}