package br.com.guisantos.datastorage.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Animal(
    @ColumnInfo(name = "name") var animalName: String?,
    @ColumnInfo(name = "gender") var animalGender: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    companion object {
        val UNDEFINED = 0
        val MACHO = 1
        val FEMEA = 2

        fun genderValidator(gender: Int?) : String {
            if(gender == MACHO)
                return "Macho"
            else if(gender == FEMEA)
                return "Fêmea"
            return "Sem informações"
        }
    }
}