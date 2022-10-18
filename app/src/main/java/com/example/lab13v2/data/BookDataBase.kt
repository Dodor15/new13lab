package com.example.lab13v2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab13v2.data.models.BookType
import com.example.lab13v2.data.models.StyleType

@Database(entities = [BookType::class, StyleType::class], version = 2)

abstract  class BookDataBase: RoomDatabase(){

    abstract fun bookDAO(): BookDAO
}