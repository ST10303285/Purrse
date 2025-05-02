package com.example.purrse.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
    ForeignKey(
        entity = Category::class,
        parentColumns = ["categoryId"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"]), Index(value=["categoryId"])]
)


data class Expense(
    @PrimaryKey(autoGenerate = true) //tells RoomDB to autogenerate IDs for each user
    val expenseId: Int = 0,

    val userId: Int,
    val categoryId : Int?,
    val amount: Double,
    val date: String,
    val startTime : String? = null,
    val endTime: String? = null,
    val description: String?,
    val receipt: String?


)