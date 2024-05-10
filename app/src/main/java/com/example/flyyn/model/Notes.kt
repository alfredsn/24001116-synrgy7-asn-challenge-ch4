package com.example.flyyn.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean
)