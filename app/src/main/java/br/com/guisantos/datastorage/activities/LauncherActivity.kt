package br.com.guisantos.datastorage.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.guisantos.datastorage.R
import br.com.guisantos.datastorage.adapters.AnimalAdapterView
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
import br.com.guisantos.datastorage.database.entities.Animal
import br.com.guisantos.datastorage.database.tasks.SearchAnimalsTask
import br.com.guisantos.datastorage.views.AnimalView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LauncherActivity : AppCompatActivity() {
    private var database: AnimalDatabase? = null
    private var dao: AnimalDao? = null
    private var animalView : AnimalView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        database = AnimalDatabase.getInstance(this)
        dao = database?.getAnimalDao()
        fabAddAnimal()

        val recyclerView: RecyclerView = findViewById(R.id.ac_launcher_animal_list)
        animalView = AnimalView(
                recyclerView,
                AnimalAdapterView(this),
                LinearLayoutManager(this),
                this,
                dao!!
        )
        animalView!!.init()

    }

    override fun onResume() {
        super.onResume()
        SearchAnimalsTask(dao!!, onFoundAnimalsListener(animalView!!)).execute()
    }


    fun fabAddAnimal() {
        findViewById<FloatingActionButton>(R.id.ac_launcher_btn_adicionar).setOnClickListener(
                View.OnClickListener() {
                    view: View -> ActivityCompat.startActivity(this, Intent(this, FormAnimalActivity::class.java), ActivityOptionsCompat.makeCustomAnimation(this,
                    R.anim.fade_in,
                    R.transition.right_to_left)
                    .toBundle())
                }
        )
    }

    class onFoundAnimalsListener(private val animalView: AnimalView) : SearchAnimalsTask.AnimalsFoundListener {
        override fun whenFound(animals: MutableList<Animal>) {
            this.animalView.uptadeAdapter(animals)
        }
    }
}