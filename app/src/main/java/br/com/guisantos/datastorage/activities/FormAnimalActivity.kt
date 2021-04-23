package br.com.guisantos.datastorage.activities

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.guisantos.datastorage.R
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import br.com.guisantos.datastorage.types.Extras
import br.com.guisantos.datastorage.utils.PermissionsUtils


class FormAnimalActivity : AppCompatActivity() {
    private var database: AnimalDatabase? = null
    private var dao: AnimalDao? = null
    private var animalNameField: EditText? = null
    private var animal: Animal = Animal("", Animal.UNDEFINED)
    private var toEdit: Boolean = false;
    private var imageViewAnimal: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_animal)
        database = AnimalDatabase.getInstance(this)
        dao = database?.getAnimalDao()
        animalNameField = findViewById(R.id.ac_form_animal_name)
        imageViewAnimal = findViewById(R.id.ac_form_animal_image_picture)

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
        imageViewConfig()
        super.onResume()
    }

    /* Configurações do Menu do App Bar */
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

    /* Configurações e Eventos do Radio Groupo Genero */
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

    /* Configurações e Ações do Image View */

    private fun onClickListenerImageView(view: View) {
        if(validaPermissoes()) {
            Toast.makeText(this, "Permitiu já", Toast.LENGTH_SHORT).show()
        } else {
            PermissionsUtils.CameraPermissions.requestCameraPermission(this)
        }
    }


    private fun imageViewConfig() {
        imageViewAnimal?.setOnClickListener(View.OnClickListener { view -> onClickListenerImageView(view) })
    }

    /* Gerenciamento de Permissões para essa Activity */

    private fun validaPermissoes(): Boolean {
        return PermissionsUtils.CameraPermissions.isCameraPermissionGranted(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PermissionsUtils.CameraPermissions.RECORD_AUDIO_PERMISSION_CODE) {
            if(grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permitiu concedida", Toast.LENGTH_SHORT).show()
            } else {
                permissionDeniedDialog()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /* Dialogo de Permissão Negada */
    private fun permissionDeniedDialog() {
        var dialogPermissionDenied = AlertDialog.Builder(this)
        dialogPermissionDenied.setTitle("Permissão Negada")
        dialogPermissionDenied.setMessage("Precisamos dessa permissão para o correto funcionamento e possibilitar inclusão de imagens. Podemos tentar novamente?")
        dialogPermissionDenied.setPositiveButton(
            "Sim",
            DialogInterface.OnClickListener { x, y ->
                PermissionsUtils.CameraPermissions.requestCameraPermission(
                    this
                )
            }
        )
        dialogPermissionDenied.setNegativeButton("Não", null);
        dialogPermissionDenied.show();
    }

    override fun finish() {
        database?.close()
        super.finish()
    }
}