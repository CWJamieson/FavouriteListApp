package com.example.favlistapp

import androidx.compose.runtime.mutableStateOf

data class Contact(val id: Int,
                   val name: String,
                   var timestamp: Long,
                   var isFav: Boolean = false)


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