package com.example.favlistapp

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactModel {
    val contacts = mutableStateOf(mutableListOf<Contact>())

    init {
        loadData()
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            contacts.value.addAll(testList)
        }
    }
}