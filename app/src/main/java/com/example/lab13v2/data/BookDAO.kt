package com.example.lab13v2.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lab13v2.data.models.BookType
import com.example.lab13v2.data.models.StyleType


@Dao
interface BookDAO {
    @Query("SELECT * FROM $BOOK_TABLE")
    fun getAllBook(): LiveData<List<BookType>>

    @Insert
    fun addBook(bookType: BookType)
    @Update
    fun saveBook(bookType: BookType)
    @Delete
    fun KillBook(bookType: BookType)

    @Query("SELECT * FROM $STYLE_TABLE WHERE _id=:id")
    fun getStyle(id:Int): StyleType

    @Query("SELECT * FROM $STYLE_TABLE")
    fun getAllStyle(): LiveData<List<StyleType>>

    @Insert
    fun addStyle(styleType: StyleType)
    @Update
    fun saveStyle(styleType: StyleType)
    @Delete
    fun killStyle(styleType: StyleType)


}