package com.example.favlistapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class ContactModel: ViewModel() {
    val contacts = mutableStateOf(mutableListOf<Contact>())
    val isLoading = mutableStateOf(false)

    init {
        loadData()
    }

    fun loadData() {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                contacts.value = testList.toMutableList()
                isLoading.value = false
            }
        }
    }
}