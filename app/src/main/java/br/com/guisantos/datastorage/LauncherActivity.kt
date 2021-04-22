package br.com.guisantos.datastorage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.guisantos.datastorage.activities.FormAnimalActivity
import br.com.guisantos.datastorage.adapters.AnimalAdapterView
import br.com.guisantos.datastorage.database.AnimalDatabase
import br.com.guisantos.datastorage.database.dao.AnimalDao
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
                    view: View -> ActivityCompat.startActivity(this, Intent(this, FormAnimalActivity::class.java), ActivityOptionsCompat.makeCustomAnimation(this, R.anim.fade_in, R.transition.right_to_left).toBundle())
                }
        )
    }
}