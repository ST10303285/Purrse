package com.example.purrse.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user") //database table called 'user'
data class User(
    @PrimaryKey(autoGenerate = true) //tells RoomDB to autogenerate IDs for each user
    val userId: Int =0,

    val username: String, //store user username
    val password: String //store user password
)
