package br.com.guisantos.datastorage.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import br.com.guisantos.datastorage.R
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal


class FormAnimalActivity : AppCompatActivity() {
    private var database: AnimalDatabase? = null
    private var dao: AnimalDao? = null
    private var animalNameField: EditText? = null
    private var animal: Animal = Animal("", Animal.UNDEFINED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_animal)
        database = AnimalDatabase.getInstance(this)
        dao = database?.getAnimalDao()
    }

    override fun onResume() {
        animalNameField = findViewById(R.id.ac_form_animal_name)
        radioGroupConfig()
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
            animal.animalName = animalNameField?.text.toString()
            dao?.create(animal)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onRadioButtonClicked(view: View): Int {
        if(view is RadioButton) {
            val checked = view.isChecked
            when(view.id) {
                R.id.ac_form_animal_radio_macho -> if(checked) return Animal.MACHO
                R.id.ac_form_animal_radio_femea -> if(checked) return Animal.FEMEA
                else return Animal.UNDEFINED
            }
        }
        return Animal.UNDEFINED
    }

    private fun radioGroupConfig() {
        findViewById<RadioGroup>(R.id.ac_form_animal_gender_group).setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.ac_form_animal_radio_macho) {
                animal.animalGender = Animal.MACHO
            } else {
                animal.animalGender = Animal.FEMEA
            }
        })
    }

    override fun finish() {
        database?.close()
        super.finish()
    }
}