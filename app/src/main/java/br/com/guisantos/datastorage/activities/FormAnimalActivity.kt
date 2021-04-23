package br.com.guisantos.datastorage.activities

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.com.guisantos.datastorage.R
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import br.com.guisantos.datastorage.types.Extras


class FormAnimalActivity : AppCompatActivity() {
    private var database: AnimalDatabase? = null
    private var dao: AnimalDao? = null
    private var animalNameField: EditText? = null
    private var animal: Animal = Animal("", Animal.UNDEFINED)
    private var toEdit: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_animal)
        database = AnimalDatabase.getInstance(this)
        dao = database?.getAnimalDao()
        animalNameField = findViewById(R.id.ac_form_animal_name)
        if(intent.hasExtra(Extras.ANIMAL_TO_EDIT)) {
            toEdit = true
            var uid = intent.getSerializableExtra(Extras.ANIMAL_TO_EDIT) as Int
            animal = dao!!.getAnimal(uid)
            animalNameField!!.setText(animal.animalName!!, TextView.BufferType.EDITABLE)
            checkRadioButton()
        }
    }

    override fun onResume() {
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
            if(toEdit)
                dao?.update(animal)
            else
                dao?.create(animal)
            finish()
        }
        return super.onOptionsItemSelected(item)
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

    private fun checkRadioButton() {
        var radioButtonFemea: RadioButton = findViewById(R.id.ac_form_animal_radio_femea)
        var radioButtonMacho: RadioButton = findViewById(R.id.ac_form_animal_radio_macho)
        if(animal.animalGender == Animal.FEMEA) {
            radioButtonFemea.isChecked = true
        } else if(animal.animalGender == Animal.MACHO) {
            radioButtonMacho.isChecked = true
        }
    }

    override fun finish() {
        database?.close()
        super.finish()
    }
}