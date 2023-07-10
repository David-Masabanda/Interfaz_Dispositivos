package com.masabanda.app.data.entities.marvel.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MarvelCharsDB (@PrimaryKey(autoGenerate = true) val id: Int,
                          val name: String,
                          val comic: String,
                          val image: String


    ): Parcelable