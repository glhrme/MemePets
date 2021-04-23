package br.com.guisantos.datastorage.database.entities

import android.text.Editable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Animal(
    @ColumnInfo(name = "name") val animalName: String?,
    @ColumnInfo(name = "gender") val animalGender: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    companion object {
        val UNDEFINED = 0
        val MACHO = 1
        val FEMEA = 2
    }
}