package com.example.purrse.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "goals",  //database table called 'user'
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)


data class Goal(
    @PrimaryKey(autoGenerate = true) //tells RoomDB to autogenerate IDs for each user
    val goalId: Int = 0,

    val userId: Int,
    val minGoal: Double?,
    val maxGoal: Double,
    val month: String //store user username

)