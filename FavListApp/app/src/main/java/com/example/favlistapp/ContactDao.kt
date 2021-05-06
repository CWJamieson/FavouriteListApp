package com.example.favlistapp

import androidx.room.*

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): List<Contact>

    @Insert
    fun insertAll(vararg users: Contact)

    @Update
    fun updateAll(vararg users: Contact)

    @Delete
    fun delete(user: Contact)
}