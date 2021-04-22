package br.com.guisantos.datastorage.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Animal (
    @ColumnInfo(name = "name") val animalName: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}