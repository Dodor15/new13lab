package com.example.lab13v2.data.models

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lab13v2.data.BOOK_TABLE

@Entity(tableName = BOOK_TABLE)
data class BookType(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = BaseColumns._ID)
    val id: Int,
    var stule:String,
    var title:String,
    var authors:String,
    var index:String,
    var pageCount:Int
)