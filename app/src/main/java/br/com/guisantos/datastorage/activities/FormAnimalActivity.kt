package br.com.guisantos.datastorage.activities

import android.app.Activity
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import br.com.guisantos.datastorage.R
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import br.com.guisantos.datastorage.types.Extras
import br.com.guisantos.datastorage.utils.PermissionsUtils
import br.com.guisantos.datastorage.utils.UtilsUri.Companion.getRealPathFromURI
import java.io.File


class FormAnimalActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    private var database: AnimalDatabase? = null
    private var dao: AnimalDao? = null
    private var animalNameField: EditText? = null
    private var animal: Animal = Animal("", Animal.UNDEFINED)
    private var toEdit: Boolean = false;
    private var imageViewAnimal: ImageView? = null
    private var imagemUri: Uri? = null

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
            if(animal.imageString != Animal.EMPTY) {
                imageViewAnimal!!.setImageURI(
                    Uri.parse(
                        File(animal.imageString).toString()
                    )
                )
            }
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
        findViewById<RadioGroup>(R.id.ac_form_animal_gender_group).setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
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
    private fun imageViewConfig() {
        imageViewAnimal?.setOnClickListener(View.OnClickListener { view ->
            onClickListenerImageView(
                view
            )
        })
    }

    private fun onClickListenerImageView(view: View) {
        if(PermissionsUtils.FormAnimalPermission.isAvatarPermissionsGranted(this)) {
            showPopUp(view)
        } else {
            PermissionsUtils.FormAnimalPermission.requestAvatarPermission(this)
        }
    }

    // Menu do Image View
    private fun showPopUp(view: View) {
        PopupMenu(this, view).apply {
            setOnMenuItemClickListener(this@FormAnimalActivity)
            inflate(R.menu.ac_form_menu_context_pictures_avatar)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.ac_form_animal_menu_galeria -> getImageGalery()
            R.id.ac_form_animal_menu_camera -> getCameraImage()
        }
        return false
    }


    /* Gerenciamento de Permissões para essa Activity */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PermissionsUtils.FormAnimalPermission.CAMERA_PERMISSION_CODE || requestCode == PermissionsUtils.FormAnimalPermission.READ_STORAGE_PERMISSION_CODE || requestCode == PermissionsUtils.FormAnimalPermission.WRITE_STORAGE_PERMISSION_CODE) {
            if(grantResults.firstOrNull() == PackageManager.PERMISSION_DENIED) {
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
                PermissionsUtils.FormAnimalPermission.requestAvatarPermission(
                    this
                )
            }
        )
        dialogPermissionDenied.setNegativeButton("Não", null);
        dialogPermissionDenied.show();
    }

    /* Configurações de Camera para tirar a foto */

    private fun getCameraImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val authorization = "br.com.guisantos.datastorage"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis())
            val resolver = contentResolver

            imagemUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val nome = directory.path + "/FS" + System.currentTimeMillis() + ".jpg"
            val file = File(nome)
            imagemUri = FileProvider.getUriForFile(baseContext, authorization, file)
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagemUri)
        startActivityForResult(intent, PermissionsUtils.FormAnimalPermission.CAMERA_PERMISSION_CODE)
    }

    /* Configurações para Obter imagem da Galeria */

    private fun getImageGalery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        startActivityForResult(
            Intent.createChooser(intent, "Escolha uma foto"),
            PermissionsUtils.FormAnimalPermission.GALERY_STORAGE_CODE
        )
    }

    /*Resultado da Camera / Galeria */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                PermissionsUtils.FormAnimalPermission.GALERY_STORAGE_CODE -> addImageAnimal(data?.data)
                PermissionsUtils.FormAnimalPermission.CAMERA_PERMISSION_CODE -> addImageAnimal(
                    imagemUri
                )
            }
        }
    }

    private fun addImageAnimal(data: Uri?) {
        val imageString = getRealPathFromURI(data!!, this)
        if(imageString is String) {
            imageViewAnimal!!.setImageURI(
                Uri.parse(
                    File(imageString).toString()
                )
            )
            animal.imageString = imageString
        }
    }

    override fun finish() {
        database?.close()
        super.finish()
    }
}