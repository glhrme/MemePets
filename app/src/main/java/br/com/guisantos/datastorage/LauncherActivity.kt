package br.com.guisantos.datastorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.guisantos.datastorage.activities.FormAnimal
import br.com.guisantos.datastorage.adapters.AnimalAdapterView
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import br.com.guisantos.datastorage.views.AnimalView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LauncherActivity : AppCompatActivity() {
    private var database: AnimalDatabase? = null
    private var dao: AnimalDao? = null
    private var adapter: AnimalAdapterView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        database = AnimalDatabase.getInstance(this)
        dao = database?.getAnimalDao()
        fabAddAnimal()
    }

    override fun onResume() {
        super.onResume()
        val recyclerView: RecyclerView = findViewById(R.id.ac_launcher_animal_list)
        AnimalView(dao!!.getAll(), recyclerView, this).init()
    }

    fun fabAddAnimal() {
        findViewById<FloatingActionButton>(R.id.ac_launcher_btn_adicionar).setOnClickListener(
                View.OnClickListener() {
                    view: View -> startActivity(Intent(this, FormAnimal::class.java))
                }
        )
    }
}