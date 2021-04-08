package com.example.favlistapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class ContactModel: ViewModel() {
    val contacts = mutableListOf<Contact>().apply { addAll(testList) }

    init {
        loadData()
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1)
            withContext(Dispatchers.Main) {
                contacts.addAll(testList)
            }
        }
    }

    fun updateContact(id: Int, timestamp: Long? = null, isFav: Boolean? = null) {
        contacts.firstOrNull { id == it.id }?.let { contact ->
            timestamp?.let {
                contact.timestamp = it
            }
            isFav?.let {
                contact.isFav = it
            }
        }
    }
}