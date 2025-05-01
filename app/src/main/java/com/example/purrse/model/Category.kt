package com.example.purrse.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",  //database table called 'user'
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE )
    ]
)


data class Category(
    @PrimaryKey(autoGenerate = true) //tells RoomDB to autogenerate IDs for each user
    val categoryId: Int = 0,
    val userId: Int,

    val name: String //store user username

)