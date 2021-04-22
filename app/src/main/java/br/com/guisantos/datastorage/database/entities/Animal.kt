package br.com.guisantos.datastorage.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Animal (
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "name") val animalName: String?
)