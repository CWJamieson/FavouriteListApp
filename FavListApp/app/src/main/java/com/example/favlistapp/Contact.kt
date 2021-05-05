package com.example.favlistapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(@PrimaryKey val id: Int,
                   @ColumnInfo val name: String,
                   @ColumnInfo var timestamp: Long,
                   @ColumnInfo var isFav: Boolean = false)


val testList = listOf(
    Contact(0, "Amber", 0L),
    Contact(1, "Andrii", 1L),
    Contact(2, "Carter", 2L),
    Contact(3, "Chris", 3L),
    Contact(4, "John", 4L),
    Contact(5, "Malachi", 5L),
    Contact(6, "Richard", 6L),
    Contact(7, "Shawn", 7L),
    Contact(8, "Thiago", 8L),
    Contact(9, "Tyler", 9L)
)