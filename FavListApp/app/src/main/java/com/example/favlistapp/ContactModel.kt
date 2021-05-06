package com.example.favlistapp

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.*

class ContactModel(): ViewModel() {
    val contacts = mutableStateOf(mutableListOf<Contact>())
    val isLoading = mutableStateOf(false)


    fun updateData(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "contact"
            ).build()
            val userDao = db.contactDao()
            userDao.updateAll(*contacts.value.toTypedArray())
        }
    }

    fun loadData(context: Context) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "contact"
            ).build()
            val userDao = db.contactDao()
            var users: List<Contact> = userDao.getAll()
            // There is no saved contacts, load the default list
            if (users.isEmpty()) {
                users = testList
                userDao.insertAll(*users.toTypedArray())
            }
            withContext(Dispatchers.Main) {
                contacts.value = users.toMutableList()
                isLoading.value = false
            }
        }
    }
}